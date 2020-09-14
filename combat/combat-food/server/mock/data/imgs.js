// 抓取的脚本
/***  
 * 

let oneData = {
  imgUrl: '',
  title: '',
  publisher: ''
}
let data = [];
$('#listtyle1_list .listtyle1').each(function(index, item){
  let img = $(item).find('img');
  let strong = $(item).find('strong');
  let em = $(item).find('em');

  let d = Object.assign({}, oneData);
  d.imgUrl = img.attr('src');
  d.title = strong.text().trim();
  d.publisher = em.text().trim();

  data.push(d);
})
console.log(JSON.stringify(data, null, 2))
*/

