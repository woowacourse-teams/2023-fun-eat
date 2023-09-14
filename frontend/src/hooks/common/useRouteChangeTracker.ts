import { useEffect, useState } from 'react';
import ReactGA from 'react-ga4';
import { useLocation } from 'react-router-dom';

import { ENVIRONMENT } from '@/constants';

const useRouteChangeTracker = () => {
  const location = useLocation();
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    if (process.env.NODE_ENV === 'development') return;
    if (ENVIRONMENT === 'dev') return;

    ReactGA.initialize(process.env.GOOGLE_ANALYTICS_ID as string);
    setInitialized(true);
  }, []);

  useEffect(() => {
    if (initialized) {
      ReactGA.send({ hitType: 'pageview', location: location.pathname });
    }
  }, [initialized, location]);
};

export default useRouteChangeTracker;
