import { useLocation } from 'react-router-dom';

const useDisplaySlice = <T>(path: string, data?: T[], limit = 2): T[] => {
  const location = useLocation();
  const isHomePage = location.pathname === path;

  return isHomePage ? data?.slice(0, limit) || [] : data || [];
};

export default useDisplaySlice;
