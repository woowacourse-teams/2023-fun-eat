import { Button, Divider, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SORT_OPTIONS } from '@constants';

interface BottomSheetContentProps {
  selectedOption: number;
  onOptionSelected: (optionIndex: number) => void;
  close: () => void;
}

const BottomSheetContent = ({ selectedOption, onOptionSelected, close }: BottomSheetContentProps) => {
  const handleSelectedOption = (optionIndex: number) => {
    onOptionSelected(optionIndex);
    close();
  };

  return (
    <BottomSheetContainer>
      {SORT_OPTIONS.map((option, index) => {
        const isSelected = index === selectedOption;
        return (
          <>
            <li key={option}>
              <SortOption
                color={theme.colors.white}
                textColor={isSelected ? 'inherit' : theme.textColors.info}
                variant="filled"
                size="lg"
                isSelected={isSelected}
                onClick={() => handleSelectedOption(index)}
              >
                {option}
              </SortOption>
            </li>
            {index < SORT_OPTIONS.length - 1 && <Divider variant="disabled" />}
          </>
        );
      })}
    </BottomSheetContainer>
  );
};

export default BottomSheetContent;

const BottomSheetContainer = styled.ul`
  padding: 20px;
`;

const SortOption = styled(Button)<{ isSelected: boolean }>`
  margin: 20px 0 10px 0;
  padding: 0;
  border: none;
  outline: transparent;
  font-weight: ${({ isSelected, theme }) => (isSelected ? theme.fontWeights.bold : 'inherit')};
  cursor: pointer;

  &:hover {
    font-weight: ${({ theme }) => theme.fontWeights.bold};
    color: ${({ theme }) => theme.textColors.default};
    transition: all 200ms ease-in;
  }
`;
