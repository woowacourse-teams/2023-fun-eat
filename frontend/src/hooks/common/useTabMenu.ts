import { useState } from 'react';

const INIT_TAB_INDEX = 0;

const useTabMenu = () => {
  const [selectedTabMenu, setSelectedTabMenu] = useState(INIT_TAB_INDEX);

  const isFirstTabMenu = selectedTabMenu === INIT_TAB_INDEX;

  const handleTabMenuClick = (index: number) => {
    setSelectedTabMenu(index);
  };

  const initTabMenu = () => {
    setSelectedTabMenu(INIT_TAB_INDEX);
  };

  return {
    selectedTabMenu,
    isFirstTabMenu,
    handleTabMenuClick,
    initTabMenu,
  };
};

export default useTabMenu;
