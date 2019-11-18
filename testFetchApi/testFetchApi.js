import fetch, { Headers } from 'node-fetch';
// const headers = new Headers();
// headers.append('Accept', 'application/json')
const url = 'http://localhost:8010/eureka/apps';
const response = await fetch(url, {
    headers: {
        Accept: 'application/json'
    }})
    // .then(response => response.json())
    // .then((response) => {
    //     return response.applications.application;
    // })
    // .catch(err => console.log(err));

const data = await response.json();
const apps_data = data.applications.application;
const list_of_active_apps = [];
apps_data.forEach(element => {
    list_of_active_apps.push(element.name)
});
console.log(list_of_active_apps)
