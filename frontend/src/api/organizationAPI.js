import axios from "axios";

const API_SERVER_HOST = 'http://localhost:80/it'

const host = `${API_SERVER_HOST}/organization`

const header = {headers: {"Content-Type": "x-www.form.urlencoded"}};

export const list = async () => {
    const res = await axios.get();   
    console.log(res.data);
    return res.data;
}
