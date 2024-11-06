import axios from "axios";
import { createContext } from "react";

const API_SERVER_HOST = 'http://localhost:80/it'

const host = `${API_SERVER_HOST}/members`

const header = {headers: {"Content-Type": "x-www-form-urlencoded"}}




export const loginPost = async (loginParam) => {
    
    const form = new FormData()
    form.append('username', loginParam.email)
    form.append('password', loginParam.pw)

    const res = await axios.post(`${host}/login`, form, header) 

    return res.data
}

export const registerMember = async (registerParam) => {


   

    console.log(registerParam)

    const form = new FormData()
    form.append('email', registerParam.email)
    form.append('pw', registerParam.pw)
    form.append('name', registerParam.name)
    form.append('birth', registerParam.birth)
    form.append('tel', registerParam.tel)
    form.append('sex', registerParam.sex)
    form.append('marital_status', registerParam.marital_Status)
    form.append('children_count', registerParam.children_count)
    form.append('qaulifications', registerParam.qualifications)
    form.append('education', registerParam.education)
    form.append('antecedents', registerParam.antecedents)
    
    console.log(form);
   
    const res = await axios.post(`${host}/register`, form, header)

    return res.data
}

export const statusRead = async (mno) => {
    

    const res = await axios.get(`${host}/statusRead?mno=${mno}`, header)

    console.log(`${mno}`);

    console.log("get으로 받은 정보 : " + res)

    console.log(JSON.stringify(res))

    return res.data  
}

export const modifyMember = async (modifyParam) => {
  

    console.log(modifyParam)

    const form = new FormData()
    form.append('email', modifyParam.email)
    form.append('pw', modifyParam.pw)
    form.append('tel', modifyParam.tel)    
    form.append('marital_status', modifyParam.marital_Status)
    form.append('children_count', modifyParam.children_count)
    form.append('qaulifications', modifyParam.qualifications)
    form.append('education', modifyParam.education)
    form.append('antecedents', modifyParam.antecedents)
    
    console.log(form);
   
    const res = await axios.put(`${host}/modify`, form, header)

    return res.data
}

