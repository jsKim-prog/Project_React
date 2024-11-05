import ReadComponent from "../../components/project/ReadComponent";
import {useParams} from "react-router-dom";




const ProjectRead = () => {
    const {id} = useParams()
    return(
        <div>
        <div>
            Project Read Page
        </div>
            <ReadComponent id={id}/>
        </div>
    );
}

export default ProjectRead;