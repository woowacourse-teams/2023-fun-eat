import { Outlet } from 'react-router-dom';

import Layout from './components/Common/Layout/Layout';

const App = () => (
  <Layout>
    <Outlet />
  </Layout>
);

export default App;
