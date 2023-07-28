import { Button } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { SortOption } from '@/types/common';

interface SortOptionListProps {
  options: readonly SortOption[];
  selectedOption: SortOption;
  selectSortOption: (selectedOptionLabel: SortOption) => void;
  close: () => void;
}

const SortOptionList = ({ options, selectedOption, selectSortOption, close }: SortOptionListProps) => {
  const handleSelectedOption = (sortOption: SortOption) => {
    selectSortOption(sortOption);
    close();
  };

  return (
    <SortOptionListContainer>
      {options.map((sortOption) => {
        const isSelected = sortOption.label === selectedOption.label;
        return (
          <li key={sortOption.label}>
            <SortOptionButton
              type="button"
              customWidth="100%"
              customHeight="100%"
              textColor={isSelected ? 'default' : 'sub'}
              size="lg"
              weight={isSelected ? 'bold' : 'regular'}
              variant="transparent"
              onClick={() => handleSelectedOption(sortOption)}
            >
              {sortOption.label}
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
