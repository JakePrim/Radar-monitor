import KCom1 from './K.js';
import kkb from '../lib/kkb.js';

// Vue.prototype.getData=function(){
//     console.log('getData');
// }
/*
    第三方库
*/
Vue.use(kkb,{
    a:1
});

let app = new Vue({
    el:"#app",
    components:{
        'k-com1':KCom1
    }
});