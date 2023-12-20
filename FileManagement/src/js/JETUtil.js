var UTIL ={
  server_url : "http://localhost:8080/api/",
  app_name : "Scheduler Management System",
  customer_name : "Company",
  env_name : "DV",

  message_timeout : 2000,

  translate: function(key){
    if(key && key.trim().length > 0){
      return oj.Translations.getTranslatedString(key);
    }
  }
}


async function callGetService(serviceName) {
  return new Promise(function(resolve, reject) {
    var headers = {};
    headers['Content-Type'] = 'application/json';

    fetch(UTIL.server_url + serviceName, {
      method: 'GET',
      headers: headers,
      mode: 'cors'
    })
    .then(resp => resp.json())
    .then((data) =>  {
      resolve(data)
    })
    .catch((error) => {
      reject(error)
    })
  })
}

async function callPostService(serviceName, input) {
  return new Promise(function(resolve, reject) {
    var formData = new FormData();
    formData.append('file', input.file);  // 'file' should match the parameter name expected by the server

    fetch(UTIL.server_url + serviceName, {
      method: 'POST',
      headers: {
        // No need to set Content-Type; browser will set it for FormData
      },
      mode: 'cors',
      body: formData,
    })
    .then(resp => resp.json())
    .then((data) =>  {
      resolve(data);
    })
    .catch((error) => {
      reject(error);
    });
  });
}





 
  