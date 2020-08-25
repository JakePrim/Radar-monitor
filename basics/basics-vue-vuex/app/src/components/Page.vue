<template>
  <div class="pagination-container">
    <div class="pagination">
      <a href class="prev">上一页</a>
      <a
        v-for="p of pages"
        :key="p"
        href
        @click.prevent="changePage(p)"
        :class="{current:p==page}"
      >{{p}}</a>
      <a href class="next">下一页</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "page",
  props: {
    //接受参数
    page: {
      type: Number,
      default: 1,
    },
    total: {
      type: Number,
      default: 0,
    },
    prepage: {
      type: Number,
      default: 1,
    },
  },
  computed: {
    pages() {
      return Math.ceil(this.total / this.prepage);
    },
  },
  methods: {
    changePage(p) {
      console.log("变了:" + p);
      this.$emit("update:page", p);
      this.page !== p && this.$emit("change", p);
    },
  },
};
</script>
