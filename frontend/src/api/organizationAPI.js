import axios from "axios";

const API_SERVER_HOST = 'http://localhost:80/it'

const host = `${API_SERVER_HOST}/organization`

const header = {headers: {"Content-Type": "x-www.form.urlencoded"}};

export const list = async (pageParam) => {
    const {page, size} = pageParam
    const res = await axios.get(`${host}/page`, {params:{page:page, size:size}});   
    console.log(res.data);

    return res.data;
}
