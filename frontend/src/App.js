import { useRoutes } from "react-router-dom";
import Themeroutes from "./routes/Router";
import { useEffect } from "react";


const App = () => {
  const routing = useRoutes(Themeroutes);

  // useEffect(()=>{
  //   document.body.style.cursor='pointer';
  // })

  return <div className="dark">{routing}</div>;
};

export default App;
