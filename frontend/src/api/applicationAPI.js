import axios from "axios";

const API_SERVER_HOST = 'http://192.168.0.212:80/it'

const host = `${API_SERVER_HOST}/application`

const header = {headers: {"Content-Type": "x-www.form.urlencoded"}};

export const list = async (pageParam) => {
    const {page, size} = pageParam    
    const res = await axios.get(`${host}/page`, {params:{page:page, size:size}});          
    console.log(res.data);

    return res.data;
}

export const add = async (formData) => {   

    console.log('ModalData : ' + formData)
    for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
    }

    const header = {headers: {"Content-Type": "multipart/form-data"}}
   
    const res = await axios.post(`${host}/register`, formData, header)

    console.log(res.data);

    return res.data
}

export const readOne = async (no) => {
    console.log('no : ' + no)
    const res = await axios.get(`${host}/getOne/${no}`);
    return res.data;    
}

export const modifyMember = async (modifyParam) => {
    console.log("api data : " + modifyParam)

    const form = new FormData();
    console.log(form);
    form.append('team', modifyParam.team)
    form.append('teamName', modifyParam.teamName)
    form.append('mno', modifyParam.mno)
    form.append('memberRole', modifyParam.memberRole)        

    const res = await axios.post(`${host}/modify`, form, header);
    console.log("api result data : " + res.data);

    return res.data;

}

