import { Button } from '@fun-eat/design-system';
import type { ForwardedRef } from 'react';
import { forwardRef, useState } from 'react';
import styled from 'styled-components';

interface TabMenuProps {
  tabMenus: string[];
}

const TabMenu = ({ tabMenus }: TabMenuProps, ref: ForwardedRef<HTMLUListElement>) => {
  const [selectedTab, setSelectedTab] = useState(0);

  const selectTabMenu = (selectedIndex: number) => {
    setSelectedTab(selectedIndex);
  };

  return (
    <TabMenuContainer ref={ref}>
      {tabMenus.map((menu, index) => {
        const isSelected = selectedTab === index;
        return (
          <TabMenuItem key={menu} isSelected={isSelected}>
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

export default forwardRef(TabMenu);

const TabMenuContainer = styled.ul`
  display: flex;
`;

const TabMenuItem = styled.li<{ isSelected: boolean }>`
  flex-grow: 1;
  width: 50%;
  height: 45px;
  border-bottom: 2px solid
    ${({ isSelected, theme }) => (isSelected ? theme.borderColors.strong : theme.borderColors.disabled)};
`;

const TabMenuButton = styled(Button)`
  padding: 0;
  line-height: 45px;
`;
