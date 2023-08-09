import { Badge, Button, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { useReviewFormActionContext } from '@/hooks/context';

interface ReviewTagItemProps {
  id: number;
  name: string;
  isSelected: boolean;
}

const ReviewTagItem = ({ id, name, isSelected }: ReviewTagItemProps) => {
  const { handleReviewFormValue } = useReviewFormActionContext();
  const theme = useTheme();

  const handleReviewTag = () => {
    handleReviewFormValue({ target: 'tagIds', value: id, isSelected });
  };

  return (
    <Button type="button" weight="bold" variant="transparent" onClick={handleReviewTag}>
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
