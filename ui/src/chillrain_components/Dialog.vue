<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';
import Button from './ts/types'; // 导入按钮类型

// 定义组件的属性和事件
const props = defineProps({
  show: {
    type: Boolean,
    default: true // 默认为 true
  },
  title: {
    type: String,
    default: "Title" // 默认标题为 "Title"
  },
  showClose: {
    type: Boolean,
    default: true // 默认显示关闭按钮
  },
  width: {
    type: String,
    default: "30%" // 默认宽度为 "30%"
  },
  top: {
    type: String,
    default: "50px" // 默认距离顶部 50px
  },
  showCancel: {
    type: Boolean,
    default: true // 默认显示取消按钮
  },
  buttons: {
    type: Array as () => Button[], // 按钮数组的类型为 Button[]
    required: true // 必需的属性
  }
});

// 定义组件的事件
const emit = defineEmits();
const close = () => {
  emit("close") // 触发 close 事件
};
</script>

<template>
  <div>
    <!-- 对话框组件 -->
    <el-dialog
        :model-value="show"
        :show-close="showClose"
        :draggable="false"
        :title="title"
        :close-on-click-modal="false"
        :width="width"
        class="cust-dialog"
        :top="top"
        @close="close"
        align-center>
      <!-- 对话框内容插槽 -->
      <div class="dialog-body">
        <slot>

        </slot>
      </div>
      <!-- 对话框底部按钮 -->
      <template v-if="(buttons || showCancel)" #footer>
        <div class="dialog-footer">
          <el-button @click="close" v-if="showCancel">
            cancel
          </el-button>
          <!-- 循环渲染按钮 -->
          <el-button v-for="btn in buttons" :type="btn.type" @click="btn.click">
            {{ btn.text }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style lang="scss">
.cust-dialog {
  margin-bottom: 10px;
  .el-dialog__body {
    padding: 0;
  }
  .dialog-body {
    border-top: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
    padding: 15px;
    min-height: 100px;
    max-height: calc(100vh - 190px);
    overflow: auto;
    margin-left: auto;
  }
  .dialog-footer {
    text-align: right;
    //padding: 10px 20px;
  }
}

</style>
<style lang="scss">

</style>
