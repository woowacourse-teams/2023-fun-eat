import { Outlet } from 'react-router-dom';

import NavigationBar from './components/Common/NavigationBar/NavigationBar';

const App = () => (
  <>
    <Outlet />
    <NavigationBar />
  </>
);

export default App;
