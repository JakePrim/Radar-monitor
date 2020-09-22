<template>
  <div class="edit">
    <div class="edit-item">
      <span class="label">修改头像</span>
      <upload-img action="/api/upload?type=user"
                  :image-url="avatar"
                  @res-url="(data)=>{avatar=data.resImgUrl}"
                  img-max-width="210"></upload-img>
    </div>
    <div class="edit-item">
      <span class="label">修改名称</span>
      <div>
        <el-input class="create-input" placeholder="请输入内容" v-model="name"></el-input>
      </div>
    </div>
    <div class="edit-item">
      <span class="label">个人简介</span>
      <div>
        <el-input class="create-input" type="textarea" placeholder="请输入内容" v-model="sign"></el-input>
      </div>
    </div>
    <div>
      <el-button class="send" type="primary" size="medium" @click="save">保存</el-button>
    </div>
  </div>
</template>

<script>
import uploadImg from '@/components/upload-img';

export default {
  name: "edit",

  components: {
    uploadImg
  },
  data() {
    const userInfo = this.$store.state.userInfo;
    return {
      name: userInfo.name,
      sign: userInfo.sign,
      avatar: userInfo.avatar
    }
  },
  methods: {
    save() {

    }
  }
}
</script>

<style lang="stylus" scoped>
.edit
  background-color #fff
  padding 10px 0 20px 20px

  .edit-item
    display flex
    margin-bottom 20px

    .label
      margin-right 10px
</style>