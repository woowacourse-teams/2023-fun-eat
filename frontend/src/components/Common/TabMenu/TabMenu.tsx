import { Button, Text, useTheme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

const PRODUCT_DETAIL_TAB_MENUS = ['리뷰', '꿀조합'];

const TabMenu = () => {
  const [selectedTab, setSelectedTab] = useState(0);

  const selectTabMenu = (selectedIndex: number) => {
    setSelectedTab(selectedIndex);
  };

  const theme = useTheme();

  return (
    <TabMenuContainer>
      {PRODUCT_DETAIL_TAB_MENUS.map((menu, index) => {
        const isSelected = selectedTab === index;
        return (
          <TabMenuItem
            key={menu}
            css={`
              border-bottom: 2px solid ${isSelected ? theme.borderColors.strong : theme.borderColors.disabled};
            `}
          >
            <Button
              color="white"
              variant="filled"
              css="width: 100%; height: 100%; padding: 0;"
              onClick={() => selectTabMenu(index)}
            >
              <Text
                as="span"
                css={`
                  line-height: 45px;
                  color: ${isSelected ? theme.textColors.default : theme.textColors.disabled};
                `}
              >
                {menu}
              </Text>
            </Button>
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
  height: 45px;
  text-align: center;
`;
