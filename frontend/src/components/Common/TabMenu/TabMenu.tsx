import { Button } from '@fun-eat/design-system';
import type { ForwardedRef, MouseEventHandler } from 'react';
import { forwardRef } from 'react';
import styled from 'styled-components';

interface TabMenuProps {
  tabMenus: readonly string[];
  selectedTabMenu: number;
  handleTabMenuSelect: (index: number) => void;
}

const TabMenu = (
  { tabMenus, selectedTabMenu, handleTabMenuSelect }: TabMenuProps,
  ref: ForwardedRef<HTMLUListElement>
) => {
  const handleTabMenuClick: MouseEventHandler<HTMLButtonElement> = (event) => {
    const { index } = event.currentTarget.dataset;

    if (index) {
      handleTabMenuSelect(Number(index));
    }
  };

  return (
    <TabMenuContainer ref={ref}>
      {tabMenus.map((menu, index) => {
        const isSelected = selectedTabMenu === index;
        return (
          <TabMenuItem key={menu} isSelected={isSelected}>
            <TabMenuButton
              type="button"
              customWidth="100%"
              customHeight="100%"
              textColor={isSelected ? 'default' : 'disabled'}
              weight={isSelected ? 'bold' : 'regular'}
              variant="transparent"
              value={menu}
              onClick={handleTabMenuClick}
              data-index={index}
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
