import { lazy } from "react";
import { Navigate } from "react-router-dom";
import { element } from "prop-types";

/****Layouts*****/
const FullLayout = lazy(() => import("../layouts/FullLayout.js"));

/***** Pages ****/

const Starter = lazy(() => import("../views/Starter.js"));
const About = lazy(() => import("../views/About.js"));
const Alerts = lazy(() => import("../views/ui/Alerts"));
const Badges = lazy(() => import("../views/ui/Badges"));
const Buttons = lazy(() => import("../views/ui/Buttons"));
const Cards = lazy(() => import("../views/ui/Cards"));
const Grid = lazy(() => import("../views/ui/Grid"));
const Tables = lazy(() => import("../views/ui/Tables"));
const Forms = lazy(() => import("../views/ui/Forms"));
const Breadcrumbs = lazy(() => import("../views/ui/Breadcrumbs"));
const Project = lazy(() => import("../pages/project/ProjectList"));
const ProjectRegister = lazy(() => import("../pages/project/ProjectRegister"))
const ProjectRead = lazy(() => import("../pages/project/ProjectRead"));
const ProjectModify = lazy(() => import("../pages/project/ProjectModify"));

const Licenselist = lazy(()=>import("../pages/distribution/LicenseAssetList"))

/*****Routes******/

const ThemeRoutes = [
  {
    path: "/",
    element: <FullLayout />,
    children: [
      {path: "/project", exact: true, element: <Project/>},
      {path: "/project/add", exact: true, element: <ProjectRegister/>},
      {path: "/project/:id", exact: true, element: <ProjectRead/>},
      {path: "/project/modify/:id", exact: true, element: <ProjectModify/>},
      { path: "/", element: <Navigate to="/starter" /> },
      { path: "/starter", exact: true, element: <Starter /> },
      { path: "/about", exact: true, element: <About /> },
      { path: "/alerts", exact: true, element: <Alerts /> },
      { path: "/badges", exact: true, element: <Badges /> },
      { path: "/buttons", exact: true, element: <Buttons /> },
      { path: "/cards", exact: true, element: <Cards /> },
      { path: "/grid", exact: true, element: <Grid /> },
      { path: "/table", exact: true, element: <Tables /> },
      { path: "/forms", exact: true, element: <Forms /> },
      { path: "/breadcrumbs", exact: true, element: <Breadcrumbs /> },
      { path: "/dist/licenses", exact: true, element:<Licenselist/>},
    ],
  },
];

export default ThemeRoutes;
