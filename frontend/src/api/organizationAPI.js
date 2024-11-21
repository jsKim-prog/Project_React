import axios from "axios";

const API_SERVER_HOST = 'http://192.168.0.212:80/it'

const host = `${API_SERVER_HOST}/organization`

const header = {headers: {"Content-Type": "x-www.form.urlencoded"}};

export const list = async (pageParam) => {
    const {page, size, searchQuery} = pageParam

    // 검색어가 있을 경우 params에 추가
    const params = { page, size };
    if (searchQuery) {
        params.searchQuery = searchQuery; // 검색어 추가
    }

    const res = await axios.get(`${host}/page`, { params });
    
    console.log(res.data);

    return res.data;
}


export const teamRead = async (mno) => {
    console.log("api data : " + mno)
    const res = await axios.get(`${host}/${mno}`); 
    console.log("api result data : " + res.data);
    
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
