import { Button, useTheme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

interface TabMenuProps {
  tabMenus: string[];
}

const TabMenu = ({ tabMenus }: TabMenuProps) => {
  const [selectedTab, setSelectedTab] = useState(0);
  const theme = useTheme();

  const selectTabMenu = (selectedIndex: number) => {
    setSelectedTab(selectedIndex);
  };

  return (
    <TabMenuContainer>
      {tabMenus.map((menu, index) => {
        const isSelected = selectedTab === index;
        return (
          <TabMenuItem
            key={menu}
            css={`
              border-bottom: 2px solid ${isSelected ? theme.borderColors.strong : theme.borderColors.disabled};
            `}
          >
            <TabMenuButton
              type="button"
              customWidth="100%"
              customHeight="100%"
              textColor={isSelected ? 'default' : 'disabled'}
              weight={isSelected ? 'bold' : 'regular'}
              variant="transparent"
              onClick={() => selectTabMenu(index)}
            >
              {menu}
            </TabMenuButton>
          </TabMenuItem>
        );
      })}
    </TabMenuContainer>
  );
};

export default TabMenu;

const TabMenuContainer = styled.ul`
  display: flex;
`;

const TabMenuItem = styled.li`
  flex-grow: 1;
  width: 50%;
  height: 45px;
`;

const TabMenuButton = styled(Button)`
  padding: 0;
  line-height: 45px;
`;
