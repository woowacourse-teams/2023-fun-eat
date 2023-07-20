import { Outlet } from 'react-router-dom';

import { Header, NavigationBar } from './components/Common';

const App = () => (
  <>
    <Header />
    <Outlet />
    <NavigationBar />
  </>
);

export default App;
