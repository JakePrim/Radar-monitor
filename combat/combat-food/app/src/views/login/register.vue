<template>
  <div class="login-section">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="top" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="用户名" prop="name">
        <el-input type="text" v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="ruleForm.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="sumitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {login, register} from '@/service/request'
export default {
  name: "register",
  data() {
    return {
      ruleForm: {
        name: '',
        password: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    sumitForm(ruleForm){
      this.$refs[ruleForm].validate(async (valid)=>{
        if (valid){
          const data = await register(this.ruleForm);
          console.log(data);
          if (data.code === 0){
            this.$message.success('注册成功');
            //请求接口 进行登录
            login({
              name: this.ruleForm.name,
              password: this.ruleForm.password
            }).then(res => {
              if (res.code === 0) {
                //存储token
                localStorage.setItem('token',res.data.token);
                //登录成功跳转到home页面
                this.$router.push('home');
              }else{
                this.$message.error(res.mes);
              }
            }).catch(error => {
              this.$message.error(error);
            });
          }else {
            this.$message.error(data.mes);
          }
        }else{
          console.log('error submit')
          return false;
        }
      })
    },
    resetForm(ruleForm){
      this.$refs[ruleForm].resetFields();
    }
  }
}
</script>

<style lang="stylus" scoped>
.login-section
  padding 0 20px
</style>