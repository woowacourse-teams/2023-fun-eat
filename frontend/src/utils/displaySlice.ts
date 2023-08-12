const displaySlice = <T>(isPage = false, data?: T[], limit = 2): T[] => {
  return isPage ? data?.slice(0, limit) || [] : data || [];
};

export default displaySlice;
