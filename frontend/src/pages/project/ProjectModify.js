import UpdateComponent from "../../components/project/UpdateComponent";
import {useParams} from "react-router-dom";

const ProjectModify = () => {
    const {id} = useParams();
    return(
    <div>
        <div>
    </div>
        ProjectModify page
        <UpdateComponent id={id}/>
    </div>
    );
}
export default ProjectModify;