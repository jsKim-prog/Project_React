import axios from "axios";

const API_SERVER_HOST = 'http://localhost:80/it'

const host = `${API_SERVER_HOST}/members`

const header = {headers: {"Content-Type": "x-www.form.urlencoded"}};




export const loginPost = async (loginParam) => {
    
    const form = new FormData();
    form.append('username', loginParam.email)
    form.append('password', loginParam.pw)

    console.log(form);
    console.log(header);

    const res = await axios.post(`${host}/login`, form, header)

    return res.data
}

export const registerMember = async (registerParam) => {   

    console.log(registerParam)

    const form = new FormData();
    console.log(form);
    form.append('email', registerParam.email);
    form.append('password', registerParam.password);
    form.append('name', registerParam.name);
    form.append('birth', registerParam.birth);
    form.append('tel', registerParam.tel);
    form.append('sex', registerParam.sex);
    form.append('marital_status', registerParam.marital_status);
    form.append('children_count', registerParam.children_count);
    form.append('qualifications', registerParam.qualifications);
    form.append('education', registerParam.education);
    form.append('antecedents', registerParam.antecedents);

    for (let [key, value] of form.entries()) {
        console.log(`${key}: ${value}`);
    }
   
    const res = await axios.post(`${host}/register`, form, header)

    console.log(res.data);

    return res.data
}

export const statusRead = async (mno) => {
    console.log(mno)    
    const res = await axios.get(`${host}/${mno}`);   
    console.log(res.data);
    return res.data;
}

export const modifyMember = async (modifyParam, mno) => {

    console.log(modifyParam);
    const form = new FormData();       
    form.append('antecedents', modifyParam.antecedents)
    form.append('birth', modifyParam.birth)
    form.append('children_count', modifyParam.children_count)
    form.append('education', modifyParam.education)
    form.append('marital_status', modifyParam.marital_status)
    form.append('email', modifyParam.email)
    form.append('password', modifyParam.password)
    form.append('name', modifyParam.name)
    form.append('qualifications', modifyParam.qualifications)
    form.append('tel', modifyParam.tel)
    form.append('sex', modifyParam.sex)
    form.append('mno', mno)

    for (let [key, value] of form.entries()) {
        console.log(`${key}: ${value}`);
    }
   
    const res = await axios.post(`${host}/modify`, form, header)

    console.log(res.data);

    return res.data    
    
}

