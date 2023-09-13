import { useState } from 'react';

const INIT_TAB_INDEX = 0;

const useTabMenu = () => {
  const [selectedTabMenu, setSelectedTabMenu] = useState(INIT_TAB_INDEX);

  const isFirstTabMenu = selectedTabMenu === INIT_TAB_INDEX;

  const handleTabMenuClick = (index: number) => {
    setSelectedTabMenu(index);
  };

  const setFirstTabMenu = () => {
    setSelectedTabMenu(INIT_TAB_INDEX);
  };

  return {
    selectedTabMenu,
    isFirstTabMenu,
    handleTabMenuClick,
    setFirstTabMenu,
  };
};

export default useTabMenu;
