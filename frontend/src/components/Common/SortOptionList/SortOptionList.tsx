import { Button, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SORT_OPTIONS } from '@/constants';

interface SortOptionListProps {
  selectedOption: number;
  selectSortOption: (optionIndex: number) => void;
  close: () => void;
}

const SortOptionList = ({ selectedOption, selectSortOption, close }: SortOptionListProps) => {
  const handleSelectedOption = (optionIndex: number) => {
    selectSortOption(optionIndex);
    close();
  };

  return (
    <SortOptionListContainer>
      {SORT_OPTIONS.map((option, index) => {
        const isSelected = index === selectedOption;
        const isLastItem = index < SORT_OPTIONS.length - 1;
        return (
          <SortOptionItem
            key={option.label}
            css={`
              border-bottom: ${isLastItem ? `1px solid ${theme.dividerColors.disabled}` : 'none'};
            `}
          >
            <SortOption
              color="white"
              textColor={isSelected ? 'black' : 'gray4'}
              variant="filled"
              size="lg"
              css={`
                font-weight: ${isSelected ? theme.fontWeights.bold : 'inherit'};
              `}
              onClick={() => handleSelectedOption(index)}
            >
              {option.label}
            </SortOption>
          </SortOptionItem>
        );
      })}
    </SortOptionListContainer>
  );
};

export default SortOptionList;

const SortOptionListContainer = styled.ul`
  padding: 20px;
`;

const SortOptionItem = styled.li``;

const SortOption = styled(Button)`
  margin: 20px 0 10px 0;
  padding: 0;
  border: none;
  outline: transparent;

  &:hover {
    font-weight: ${({ theme }) => theme.fontWeights.bold};
    color: ${({ theme }) => theme.textColors.default};
    transition: all 200ms ease-in;
  }
`;
