# -*- coding:utf-8 -*
import json
from threading import Thread

import cv2
import io
import os
import sys
import torch
from PIL import Image
from flask import Flask, request, Response, make_response
from werkzeug.exceptions import BadRequest
import subprocess as sp


# 创建flask app
app = Flask(__name__)
# 模型列表
models = {}
# 管道的pid
pid_list = {}
# 线程的推流标记
keys = {}
# 类型
class_names = ['fire', 'smoke', 'sleep', 'phone']

# 模型识别API 使用url中的一段作为参数，这样一个API接口可以当4个用 已完成测试
@app.route("/model/<api>/<type>", methods=['GET', 'POST'])
def detect_api(api, type):
    # 返回的json
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': 'False', 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    model = models[api]
    if model is None:
        return_dict['info'] = '错误的API'
        return json.dumps(return_dict, ensure_ascii=False)
    else:
        # 视频识别
        if type == 'live':
            get_data = request.args.to_dict()
            dst = get_data.get('dst')
            if dst is None or '':
                return_dict['info'] = '错误的API'
                return json.dumps(return_dict, ensure_ascii=False)
            # dst = rtsp_site + dst
            #必须用多线程 多进程会导致获取不到模型
            Thread(target = detect_video, args = (dst, api,)).start()
            return json.dumps(return_dict, ensure_ascii=False)
        # 图像识别
        elif type == 'img':
            file = extract_img_form_request(request)
            img_bytes = file.read()
            results = detect_img(img_bytes, model)
            results.render()
            for img in results.ims:
                RGB_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
                im_arr = cv2.imencode('.jpg', RGB_img)[1]
                response = make_response(im_arr.tobytes())
                response.headers['Content-Type'] = 'image/jpeg'
            return response


# 关闭
@app.route('/close/<api>', methods=['GET'])
def close(api):
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': 'False', 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    if pid_list[api] is None:
        return_dict['code'] = '5004'
        return_dict['info'] = '目标API不存在或已关闭！'
        return json.dumps(return_dict, ensure_ascii=False)
    # 关闭模型
    global keys
    # 关闭推流
    keys[api] = False
    # 关闭管道进程
    os.kill(pid_list[api], __signal=9)
    pid_list[api] = 0
    return json.dumps(return_dict, ensure_ascii=False)


# 重载模型， 已完成测试
@app.route('/reload', methods=['GET'])
def reload():
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': 'False', 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    load_models()
    return json.dumps(return_dict, ensure_ascii=False)


# 从request中解析图片
def extract_img_form_request(request):
    if 'file' not in request.files:
        raise BadRequest('缺少图片文件！')
    file = request.files['file']
    # 文件名为空 非法
    if file.filename == '':
        raise BadRequest('非法的文件名！')
    return file


# 识别图片 已测试
def detect_img(img_bytes, model):
    # 读取图片
    img = Image.open(io.BytesIO(img_bytes))
    # 识别
    result = model(img, size=640)
    return result


def detect_video(dst, api):
    """

    :param dst: 流媒体服务器地址
    :param api: 模型名称
    :return:
    """
    # 打开流
    cap = cv2.VideoCapture(dst)
    # 获取识别模型
    model = models[api]
    if model is None:
        raise BadRequest('模型不存在！')
    # 开启管道
    pipe = rtsp(cap, dst + '/' + api, api)
    global keys
    keys[api] = True
    while keys[api]:
        ret, frame = cap.read()
        if not ret:
            break
        # 逐帧读取并转换为 PIL 可读的图像
        pil_img = Image.fromarray(frame)
        # 进行识别
        # results = models[api](pil_img, size=640)
        results = model(pil_img, size=640)
        # 在帧上标注识别结果
        results.render()
        for det in results.xyxy[0]:
            try:
                xmin, ymin, xmax, ymax = int(det[0]), int(det[1]), int(det[2]), int(det[3])
                cv2.rectangle(frame, (xmin, ymin), (xmax, ymax), (0, 255, 0), 2)
                cv2.putText(frame, f"Class: {api}", (xmin, ymin - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
            except IndexError:
                pass
        pipe.stdin.write(frame.tobytes())
    # 释放资源
    cap.release()


# 加载模型 已测试
def load_models():
    global models, keys
    # models = {}
    print('start YoloV5 webservice...')
    # 模型文件夹
    # models_dir = 'models'
    models_dir = 'models_train'
    if len(sys.argv) > 1:
        models_dir = sys.argv[1]
    print(f'read model form {models_dir}...')
    for r, d, f in os.walk(models_dir):
        for file in f:
            if '.pt' in file:
                model_file_name = os.path.splitext(file)[0]
                model_path = os.path.join(r, file)
                print(f'load model: {file}...')
                model_name = model_file_name.split('_')[0]
                models[model_name] = torch.hub.load('./yolov5', 'custom', path=model_path, force_reload=True,
                                                    source='local')
                # models[model_name] = torch.load(model_path, map_location=torch.device("cuda:0"))
                #初始化推流标记
                keys[model_name] = True
                # 设置模型参数 概率超过50%则被认为正确
                model = models[model_name]
                # torch.save(model.state_dict(), 'models/' + model_name + "_v5.pt")
                model.conf = 0.5
    print("models load done!")


# rtsp管道 已测试
def rtsp(cap, dst, api):
    fps = int(cap.get(cv2.CAP_PROP_FPS))
    width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    command = ['ffmpeg',
               '-loglevel', 'error',
               '-y',
               '-f', 'rawvideo',
               '-vcodec', 'rawvideo',
               '-pix_fmt', 'bgr24',  # 这里改成了bgr24
               '-s', "{}x{}".format(width, height),
               '-r', str(fps),
               '-i', '-',
               '-c:v', 'libx264',
               '-pix_fmt', 'yuv420p',
               '-preset', 'ultrafast',
               '-rtsp_transport', 'tcp',
               '-f', 'rtsp',
               dst]
    pipe = sp.Popen(command, stdin=sp.PIPE)
    global pid_list
    pid_list[api] = pipe.pid
    print(r"new pipe pid = {}", pipe.pid)
    return pipe


@app.route('/')
def index():
    return "Hello, World!"


# API 入口
if __name__ == '__main__':
    load_models()
    app.run(debug=True, host='0.0.0.0', port=5001)
