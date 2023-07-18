import { Button, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SORT_OPTIONS } from '@constants';

interface SortOptionListProps {
  selectedOption: number;
  onOptionSelected: (optionIndex: number) => void;
  close: () => void;
}

const SortOptionList = ({ selectedOption, onOptionSelected, close }: SortOptionListProps) => {
  const handleSelectedOption = (optionIndex: number) => {
    onOptionSelected(optionIndex);
    close();
  };

  return (
    <SortOptionListContainer>
      {SORT_OPTIONS.map((option, index) => {
        const isSelected = index === selectedOption;
        const isLastItem = index < SORT_OPTIONS.length - 1;
        return (
          <>
            <SortOptionWrapper key={option.label} isLastItem={isLastItem}>
              <SortOption
                color={theme.colors.white}
                textColor={isSelected ? 'inherit' : theme.textColors.info}
                variant="filled"
                size="lg"
                isSelected={isSelected}
                onClick={() => handleSelectedOption(index)}
              >
                {option.label}
              </SortOption>
            </SortOptionWrapper>
          </>
        );
      })}
    </SortOptionListContainer>
  );
};

export default SortOptionList;

const SortOptionListContainer = styled.ul`
  padding: 20px;
`;

const SortOptionWrapper = styled.li<{ isLastItem: boolean }>`
  border-bottom: ${({ isLastItem, theme }) => (isLastItem ? `1px solid ${theme.dividerColors.disabled}` : 'none')};
`;

const SortOption = styled(Button)<{ isSelected: boolean }>`
  margin: 20px 0 10px 0;
  padding: 0;
  border: none;
  outline: transparent;
  font-weight: ${({ isSelected, theme }) => (isSelected ? theme.fontWeights.bold : 'inherit')};

  &:hover {
    font-weight: ${({ theme }) => theme.fontWeights.bold};
    color: ${({ theme }) => theme.textColors.default};
    transition: all 200ms ease-in;
  }
`;
