import { Outlet } from 'react-router-dom';

import { NavigationBar } from './components/Common';

const App = () => (
  <>
    <Outlet />
    <NavigationBar />
  </>
);

export default App;
