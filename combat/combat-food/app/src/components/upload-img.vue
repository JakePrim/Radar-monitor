<template>
  <el-upload class="avatar-uploader" :action="action"
             :show-file-list="false"
             :on-success="handleSuccess"
             :before-upload="beforeUpload">

    <img :src="url" class="avatar" :style="{maxWidth:imgMaxWidth+'px'}">
  </el-upload>
</template>

<script>
export default {
  name: "upload-img",
  props: {
    action:String,
    maxSize:{
      type:Number,
      default:2
    },
    imageUrl:{
      type:String,
      default:''
    },
    imgMaxWidth:{
      //设置最大宽度
      type:[Number,String],
      default:'auto'
    }
  },
  data() {
    return {
      url: this.imageUrl
    }
  },
  methods: {
    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < this.maxSize;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    handleSuccess(res,file) {
      console.log(res)
      if (res.code ===1){
        this.$message.warning(res.mes);
        return;
      }
      this.url = URL.createObjectURL(file.raw)
      this.$emit('res-url',{
        resImgUrl:res.data.url
      })
    },
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>