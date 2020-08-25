<template>
    <div id="main">
        <div v-if="isError">
            <h3>获取商品失败</h3>
        </div>
        <div v-else>
            <div v-if="item">
                <h1 class="title">{{item.name}}</h1>
                <div class="detail" v-html="item.detail.detail"></div>
            </div>
            <div v-else>
                <p>数据加载中……</p>
            </div>
        </div>
    </div>
</template>

<script>
import Axios from '@/api';
export default {
    name: 'Detail',
    data() {
        return {
            item: null,
            isError: false
        }
    },
    async created() {
        try {
            let {data} = await Axios.getItem(this.$route.params.id);
            // console.log('data', data);
            this.item = data;
        } catch(e) {
            this.isError = true;
        }
    }
}
</script>