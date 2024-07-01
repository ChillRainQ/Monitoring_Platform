import json
import logging
import os
import signal
from threading import Thread
import cv2
import subprocess as sp
from flask import Flask, request

app = Flask(__name__)
# 推流
pid = {}
sign = True


# 部署于硬件设备中的Api，用于启动与关闭摄像头
@app.route("/getConnect", methods=["GET"])
def get_connect():
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': 'False', 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '5004'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    get_data = request.args.to_dict()
    dst = get_data.get('dst')
    if dst == '':
        return json.dumps(return_dict, ensure_ascii=False)
    else:
        Thread(target=work, args=(dst,)).start()
        return json.dumps(return_dict, ensure_ascii=False)


@app.route("/close/camera", methods=["GET"])
def connect_close():
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': 'False', 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    global sign
    global pid
    sign = False
    os.kill(pid['pid'], signal.SIGTERM)
    pid['pid'] = 0
    return json.dumps(return_dict, ensure_ascii=False)


def work(dst):
    # 打开本机摄像头
    cap = cv2.VideoCapture(0)
    try:
        readVideo(cap, dst)
    except Exception as e:
        logging.info('打开摄像头异常', e)
    cap.release()


def get_pipe(cap, dst):
    fps = int(cap.get(cv2.CAP_PROP_FPS))
    width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    command = ['ffmpeg',
               '-loglevel', 'error',
               '-c',
               '-y',
               '-f', 'rawvideo',
               '-vcodec', 'rawvideo',
               '-pix_fmt', 'bgr24',
               '-s', "{}x{}".format(width, height),
               '-r', str(fps),
               '-i', '-',
               '-c:v', 'libx264',
               '-pix_fmt', 'yuv420p',
               '-preset', 'ultrafast',
               '-rtsp_transport', 'tcp',
               '-f', 'rtsp',
               # 'rtsp://127.0.0.1:8554/push']
               dst]
    # command = ['ffmpeg',
    #            '-loglevel', 'error',
    #            '-y',
    #             '-f', 'rawvideo',
    #             '-vcodec', 'rawvideo',
    #             '-pix_fmt', 'bgr24',
    #             '-s', "{}x{}".format(width, height),
    #             '-r', str(fps),
    #             '-i', '-',
    #             '-c:v', 'libx264',
    #             '-pix_fmt', 'yuv420p',
    #             '-preset', 'ultrafast',
    #             '-f', 'hls',
    #             '-hls_time', '2',            # 每片时长（单位：秒）
    #             '-hls_list_size', '1',       # 仅保留最新的一片
    #             '-hls_flags', 'delete_segments',  # 删除历史分片
    #             '127.0.0.1:8888/push/stream.m3u8']
    pipe = sp.Popen(command, stdin=sp.PIPE)
    global pid
    pid['pid'] = pipe.pid
    print('pid: ', pid['pid'])
    return pipe


def readVideo(cap, site):
    pipe = get_pipe(cap, site)
    global sign
    sign = True
    while sign:
        ret, frame = cap.read()
        # 开始推流
        pipe.stdin.write(frame.tobytes())


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0', port=5000)
