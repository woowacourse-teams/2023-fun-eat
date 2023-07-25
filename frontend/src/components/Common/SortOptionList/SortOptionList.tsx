import { Button, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { SortOption } from '@/types/common';

interface SortOptionListProps {
  options: readonly SortOption[];
  selectedOption: string;
  selectSortOption: (selectedOptionLabel: string) => void;
  close: () => void;
}

const SortOptionList = ({ options, selectedOption, selectSortOption, close }: SortOptionListProps) => {
  const handleSelectedOption = (selectedOptionLabel: string) => {
    selectSortOption(selectedOptionLabel);
    close();
  };

  return (
    <SortOptionListContainer>
      {options.map(({ label }) => {
        const isSelected = label === selectedOption;
        return (
          <li key={label}>
            <SortOptionButton
              color="white"
              textColor={isSelected ? 'default' : 'sub'}
              variant="filled"
              size="lg"
              css={`
                font-weight: ${isSelected ? theme.fontWeights.bold : 'inherit'};
              `}
              onClick={() => handleSelectedOption(label)}
            >
              {label}
            </SortOptionButton>
          </li>
        );
      })}
    </SortOptionListContainer>
  );
};

export default SortOptionList;

const SortOptionListContainer = styled.ul`
  padding: 20px;

  & > li {
    height: 60px;
    border-bottom: 1px solid ${({ theme }) => theme.dividerColors.disabled};
    line-height: 60px;
  }

  & > li:last-of-type {
    border: none;
  }
`;

const SortOptionButton = styled(Button)`
  width: 100%;
  height: 100%;
  padding: 10px 0;
  border: none;
  outline: transparent;
  text-align: left;

  &:hover {
    font-weight: ${({ theme }) => theme.fontWeights.bold};
    color: ${({ theme }) => theme.textColors.default};
    transition: all 200ms ease-in;
  }
`;
