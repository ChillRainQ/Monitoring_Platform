<script setup lang="ts">
import {reactive, ref} from 'vue';
const options = reactive({
  width: '82%', //播放器高度
  height: '200%', //播放器高度
  type: 'm3u8',
  muted: true, //静音
  autoPlay: true, //自动播放
  control: true, //是否显示控制
})
const props = defineProps({
  src:{
    type: String,
    required: true
  }
})
</script>
<template>
  <vue3VideoPlay v-bind="options" :src="src"/>
</template>
<style scoped>
</style>




