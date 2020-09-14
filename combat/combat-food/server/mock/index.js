let db = require('./db');

let argv = process.argv.slice(2);
async function init(){
  let connect = await db();
  console.log(connect);
  //require('./user');
  // require('./menu');
  for(let i = 0; i < argv.length; i++){
    await require(`./${argv[i]}.js`)();
  }
  
}

init();