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
    <Button type="button" weight="bold" variant="transparent" onClick={() => toggleTagSelection(id, isSelected)}>
      <TagBadge
        isSelected={isSelected}
        size="sm"
        color="transparent"
        textColor={theme.textColors.default}
        role={'radio'}
        aria-label={`${name} 태그`}
        aria-checked={isSelected}
      >
        {name}
      </TagBadge>
    </Button>
  );
};

export default ReviewTagItem;

const TagBadge = styled(Badge)<{ isSelected: boolean }>`
  border: 2px solid ${({ theme }) => theme.colors.primary};
  background: ${({ isSelected, theme }) => isSelected && theme.colors.primary};
  white-space: nowrap;
`;
