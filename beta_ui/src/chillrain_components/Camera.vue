<!--<script setup lang="ts">-->
<!--import { VideoPlayer } from 'vue-hls-video-player';-->
<!--import {defineProps} from "vue";-->
<!--import Button from "~/chillrain_components/ts/types";-->
<!--const props = defineProps({-->
<!--  width: {-->
<!--    type: String,-->
<!--    default: "30%" // 默认宽度为 "30%"-->
<!--  },-->
<!--  link: {-->
<!--    type: String,-->
<!--    default: ""-->
<!--  },-->
<!--  high: {-->
<!--    type: String,-->
<!--    default: "30%"-->
<!--  }-->
<!--});-->
<!--</script>-->

<!--<template>-->
<!--&lt;!&ndash;  <div>&ndash;&gt;-->
<!--&lt;!&ndash;    <video ref="videoPlayer"&ndash;&gt;-->
<!--&lt;!&ndash;           class="video-js vjs-default-skin"&ndash;&gt;-->
<!--&lt;!&ndash;           controls&ndash;&gt;-->
<!--&lt;!&ndash;           preload="auto"&ndash;&gt;-->
<!--&lt;!&ndash;           width="280"&ndash;&gt;-->
<!--&lt;!&ndash;           height="300">&ndash;&gt;-->
<!--&lt;!&ndash;      <source src="http://127.0.0.1:8888/push/index.m3u8" type="application/x-mpegURL">&ndash;&gt;-->
<!--&lt;!&ndash;    </video>&ndash;&gt;-->
<!--&lt;!&ndash;  </div>&ndash;&gt;-->
<!--  <VideoPlayer-->
<!--      type="preview"-->
<!--      previewImageLink="poster.webp"-->
<!--      :link="link"-->
<!--      :width="width"-->
<!--      :high="high"-->
<!--      autoPlay="true"-->
<!--      class="customClassName"-->
<!--  >-->
<!--  </VideoPlayer>-->
<!--</template>-->
<!--<script setup lang="ts">-->
<!--import { reactive } from 'vue';-->
<!--interface VideoOptions {-->
<!--  autoPlay: boolean;-->
<!--  loop: boolean;-->
<!--  control: boolean;-->
<!--  muted: boolean;-->
<!--  src: string;-->
<!--  poster: string;-->
<!--  type: string;-->
<!--}-->

<!--const options = reactive<VideoOptions>({-->

<!--  autoPlay:true,//是否自动播放-->
<!--  loop:true,//是否循环播放-->
<!--  control:false,//是否显示控制条-->
<!--  muted:true, //静音-->
<!--  src: "http://127.0.0.1:8888/push/index.m3u8", //视频源-->
<!--  poster: '', //封面-->
<!--  type: "m3u8"-->

<!--})-->

<!--</script>-->

<!--<template>-->

<!--  <vue3VideoPlay-->
<!--    width="100%"-->
<!--    height="100%" -->
<!--    style="object-fit: cover;"-->
<!--    :autoPlay="options.autoPlay"-->
<!--    :control="options.control"-->
<!--    :loop="options.loop"-->
<!--    :muted="options.muted"-->
<!--    :src="options.src"-->
<!--  />-->

<!--</template>-->
<!--<template>-->
<!--  <div class="box-viods">-->
<!--    <van-nav-bar-->
<!--        title="监控播放"-->
<!--        left-text="" left-arrow @click-left="$router.go(-1)" fixed-->
<!--    />-->

<!--  </div>-->
<!--  <div class="tzc_demo">-->
<!--    <video-player-->
<!--        class="video-player vjs-custom-skin"-->
<!--        ref="videoPlayer"-->
<!--        :playsinline="true"-->
<!--        :options="playerOptions"-->
<!--    >-->
<!--    </video-player>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->

<!--import { defineComponent, ref, onMounted } from "vue";-->
<!--import {-->
<!--  com_cam_list_url,-->
<!--} from "@/api/index";-->
<!--export default {-->
<!--  name: "vidoList",-->
<!--  setup() {-->
<!--    let $route = useRouter();-->
<!--    let eqery = {-->
<!--      guid: $route.currentRoute.value.params.id,-->
<!--    };-->
<!--    let video_s = ref();-->
<!--    //播放器渲染对象-->
<!--    let playerOptions = ref({-->
<!--      playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度-->
<!--      autoplay: false, // 如果为true,浏览器准备好时开始回放。-->
<!--      muted: false, // 默认情况下将会消除任何音频。-->
<!--      loop: false, // 是否视频一结束就重新开始。-->
<!--      preload: "auto", // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）-->
<!--      language: "zh-CN",-->
<!--      aspectRatio: "16:9", // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）-->
<!--      fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。-->
<!--      html5: { hls: { withCredentials: false } },-->
<!--      flash: { hls: { withCredentials: false } },-->
<!--      sources: [-->
<!--        {-->
<!--          type: "application/x-mpegURL", // 类型-->
<!--          src: '', // url地址-->
<!--        },-->
<!--      ],-->
<!--      poster: "", // 封面地址-->
<!--      notSupportedMessage: "此视频暂无法播放，请稍后再试", // 允许覆盖Video.js无法播放媒体源时显示的默认信息。-->
<!--      controlBar: {-->
<!--        timeDivider: true, // 当前时间和持续时间的分隔符-->
<!--        durationDisplay: true, // 显示持续时间-->
<!--        remainingTimeDisplay: false, // 是否显示剩余时间功能-->
<!--        fullscreenToggle: true, // 是否显示全屏按钮-->
<!--      },-->
<!--    });-->
<!--    //这里执行相对应的接口方法获取你的url地址，为播放器对象进行赋值-->
<!--    const Video_u = () => {-->
<!--      com_cam_list_url(eqery)-->
<!--          .then((res) => {-->
<!--            video_s.value = res.data.mydata;-->
<!--            playerOptions.value.sources[0].src=res.data.mydata.recurl;//这里对url赋值-->
<!--            console.log("video", playerOptions);-->
<!--          })-->
<!--          .catch((err) => {-->
<!--            console.log(err);-->
<!--          });-->
<!--    };-->
<!--    onMounted(() => {-->
<!--      Video_u();-->
<!--    });-->
<!--    return {-->
<!--      video_s,-->
<!--      Video_u,-->
<!--      playerOptions,-->
<!--    };-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped lang="less">-->
<!--.box-viods {-->
<!--  margin-bottom: 3.4rem;-->
<!--  //   .tzc_demo_bt{-->
<!--  //       width: 100%;-->
<!--  //       height: 2rem;-->
<!--  //       text-align: center;-->
<!--  //       line-height: 2rem;-->
<!--  //   }-->
<!--}-->
<!--.tzc_box_vlist {-->
<!--  margin-top: 3rem;-->
<!--  margin-bottom: 4rem;-->
<!--}-->
<!--.viods-list {-->
<!--  width: 100%;-->
<!--  box-sizing: border-box;-->
<!--  padding: 0 0.5rem;-->
<!--  padding-top: 0.5rem;-->
<!--}-->
<!--.list-title {-->
<!--  width: 100%;-->
<!--  height: 2.3rem;-->
<!--  background: #007acc;-->
<!--  color: white;-->
<!--  line-height: 2.4rem;-->
<!--  font-weight: bold;-->
<!--  font-size: 1rem;-->
<!--  box-sizing: border-box;-->
<!--  padding: 0 0.8rem;-->
<!--  border-radius: 6px;-->
<!--}-->
<!--.list-video {-->
<!--  width: 100%;-->
<!--  height: 10rem;-->
<!--  overflow: hidden;-->
<!--  background: rgb(233, 246, 255);-->
<!--  position: relative;-->
<!--  .tzc_icon_video {-->
<!--    display: inline-block;-->
<!--    position: absolute;-->
<!--    left: 50%;-->
<!--    top: 50%;-->
<!--    transform: translate(-50%, -50%);-->
<!--    font-size: 4rem;-->
<!--    color: #007acc;-->
<!--  }-->
<!--  // img {-->
<!--  //   width: 100%;-->
<!--  //   height: auto;-->
<!--  // }-->
<!--}-->
<!--/deep/ .van-nav-bar .van-icon {-->
<!--  color: #333333;-->
<!--}-->
<!--/deep/ .van-nav-bar__title {-->
<!--  font-weight: bold;-->
<!--  font-size: 1rem;-->
<!--}-->
<!--</style>-->
<!--<template>-->
<!--  <div class="video">-->
<!--    <video-player class="video-player vjs-custom-skin"-->
<!--                  ref="videoPlayer"-->
<!--                  :playsinline="true"-->
<!--                  :options="data.playerOptions">-->
<!--    </video-player>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import { reactive } from '@vue/reactivity'-->

<!--export default {-->
<!--  name: 'Video',-->
<!--  props:['video','cover'],-->
<!--  setup(props){-->
<!--    let data = reactive({-->
<!--      playerOptions: {-->
<!--        playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度-->
<!--        autoplay: true, // 如果为true,浏览器准备好时开始回放。-->
<!--        muted: false, // 默认情况下将会消除任何音频。-->
<!--        loop: false, // 是否视频一结束就重新开始。-->
<!--        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）-->
<!--        language: 'zh-CN',-->
<!--        aspectRatio: '4:3', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）-->
<!--        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。-->
<!--        sources: [{-->
<!--          type: "video/mp4", // 类型-->
<!--          src: 'https://vd3.bdstatic.com/mda-maunc8q8gpwgjts8/sc/cae_h264_nowatermark/1611907885/mda-maunc8q8gpwgjts8.mp4?v_from_s=hkapp-haokan-hbf&auth_key=1660703434-0-0-645160ca4ccdbc9c0c2088e7d56dd8b9&bcevod_channel=searchbox_feed&pd=1&cd=0&pt=3&logid=0034644783&vid=11220038343555649881&abtest=103890_1-103579_2&klogid=0034644783' // url地址-->
<!--          // src: props.video // url地址-->
<!--        }],-->
<!--        poster: props.cover, // 封面地址-->
<!--        notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。-->
<!--        controlBar: {-->
<!--          timeDivider: true, // 当前时间和持续时间的分隔符-->
<!--          durationDisplay: true, // 显示持续时间-->
<!--          remainingTimeDisplay: false, // 是否显示剩余时间功能-->
<!--          fullscreenToggle: true // 是否显示全屏按钮-->
<!--        }-->
<!--      }-->
<!--    })-->
<!--    return {-->
<!--      data-->
<!--    }-->
<!--  }-->

<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.video{-->
<!--  width: 100%;-->
<!--  height: 100%;-->
<!--  margin: auto;-->
<!--}-->
<!--</style>-->
<!--<style>-->
<!--.video .vjs_video_3-dimensions.vjs-fluid {-->
<!--  padding-top: 57%;-->
<!--}-->
<!--.video .vjs-custom-skin > .video-js .vjs-big-play-button {-->
<!--  /* background-color: rgba(0,0,0,0.45); */-->
<!--  font-size: 3.5em;-->
<!--  border-radius: 50%;-->
<!--  height: 2em !important;-->
<!--  width: 2em !important;-->
<!--  line-height: 2em !important;-->
<!--  margin-top: -1em !important;-->
<!--  margin-left: -1em-->
<!--}-->
<!--.video .vjs-poster{-->
<!--  background-position:center-->
<!--}-->
<!--</style>-->
<!--<template>-->
<!--  <div>-->
<!--    <vue3VideoPlay-->
<!--        width="800px"-->
<!--        title="冰河世纪"-->
<!--        :src="options.src"-->
<!--        :type="options.type"-->
<!--        :autoPlay="true"-->
<!--    />-->
<!--  </div>-->
<!--</template>-->
<!--<script setup lang="ts">-->
<!--import { reactive } from 'vue';-->
<!--const options = reactive({-->
<!--  src: "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8", //视频源-->
<!--  type: 'm3u8', //视频类型-->
<!--})-->
<!--</script>-->

<!--<style scoped>-->
<!--</style>-->
<template>
<!--  <div>-->
<!--    <vue3VideoPlay v-bind="options"/>-->
<!--  </div>-->
</template>

<script setup lang="ts">
import { reactive } from 'vue';
const options = reactive({
  width: '800px', //播放器高度
  height: '450px', //播放器高度
  color: "#409eff", //主题色
  title: '', //视频名称
  src: "http://192.168.137.38:8888/push/index.m3u8", //视频源
  type: 'm3u8',
  muted: true, //静音
  webFullScreen: true,
  speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
  autoPlay: true, //自动播放
  control: false, //是否显示控制

})
</script>

<style scoped>
</style>




