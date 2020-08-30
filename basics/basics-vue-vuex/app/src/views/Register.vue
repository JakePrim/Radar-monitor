<template>
  <div id="main">
    <div id="register" class="panel">
      <h2>注册</h2>
      <form @submit="checkFrom" method="post">
        <div class="form-item">
          <label>
            <span class="txt">姓名：</span>
            <input type="text" class="form-input" v-model="name"/>
          </label>
        </div>
        <div class="form-item">
          <label>
            <span class="txt">密码：</span>
            <input type="password" class="form-input" v-model="password"/>
          </label>
        </div>
        <div class="form-item">
          <label>
            <span class="txt">重复密码：</span>
            <input type="password" class="form-input" v-model="repassword"/>
          </label>
        </div>
        <div class="form-item">
          <label>
            <span class="txt"></span>
            <button class="form-button">注册</button>
          </label>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import Axios from '@/api'
export default {
    name:"Register",
    data:function(){
      return {
        name:"",
        password:"",
        repassword:""
      }
    },
    methods: {
      checkFrom:async function(e){
        e.preventDefault();
        //注册成功后跳转到登录页面
        const data = await Axios.register({
          name:this.name,
          password:this.password,
          repassword:this.repassword
        });
        console.log(data);
        this.$router.push({name:"Login"});
      }
    },
}
</script>