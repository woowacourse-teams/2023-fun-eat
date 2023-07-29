import { Badge, Button, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

interface ReviewTagItemProps {
  id: number;
  name: string;
  isSelected: boolean;
  toggleTagSelection: (id: number, isSelected: boolean) => void;
}

const ReviewTagItem = ({ id, name, isSelected, toggleTagSelection }: ReviewTagItemProps) => {
  const theme = useTheme();

  return (
    <TagSelectButton
      type="button"
      weight="bold"
      variant="transparent"
      onClick={() => toggleTagSelection(id, isSelected)}
    >
      <TagBadge
        isSelected={isSelected}
        size="sm"
        color="transparent"
        textColor={theme.textColors.default}
        css={isSelected && `background: ${theme.colors.primary}`}
      >
        {name}
      </TagBadge>
    </TagSelectButton>
  );
};

export default ReviewTagItem;

const TagSelectButton = styled(Button)`
  &:disabled {
    opacity: 0.5;
  }
`;

const TagBadge = styled(Badge)<{ isSelected: boolean }>`
  border: 2px solid ${({ theme }) => theme.colors.primary};
  background: ${({ isSelected, theme }) => isSelected && theme.colors.primary};
  white-space: nowrap;
`;
