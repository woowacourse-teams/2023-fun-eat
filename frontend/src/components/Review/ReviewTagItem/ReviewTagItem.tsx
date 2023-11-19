import { Badge, Button, useTheme } from '@fun-eat/design-system';
import type { RuleSet } from 'styled-components';
import styled, { css } from 'styled-components';

import { useReviewFormActionContext } from '@/hooks/context';
import type { TagVariants } from '@/types/common';

interface ReviewTagItemProps {
  id: number;
  name: string;
  variant: TagVariants;
  isSelected: boolean;
}

const ReviewTagItem = ({ id, name, variant, isSelected }: ReviewTagItemProps) => {
  const { handleReviewFormValue } = useReviewFormActionContext();
  const theme = useTheme();

  const handleReviewTag = () => {
    handleReviewFormValue({ target: 'tagIds', value: id, isSelected });
  };

  return (
    <Button type="button" weight="bold" variant="transparent" onClick={handleReviewTag}>
      <TagBadge
        variant={variant}
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

type TagStyleProps = Pick<ReviewTagItemProps, 'variant' | 'isSelected'>;

type TagVariantStyles = Record<TagVariants, (isSelected: boolean) => RuleSet<object>>;

const tagColorStyles: TagVariantStyles = {
  TASTE: (isSelected) => css`
    border: 2px solid ${({ theme }) => theme.colors.tertiary};
    background: ${({ theme }) => isSelected && theme.colors.tertiary};
  `,
  QUANTITY: (isSelected) => css`
    border: 2px solid ${({ theme }) => theme.colors.secondary};
    background: ${({ theme }) => isSelected && theme.colors.secondary};
  `,
  ETC: (isSelected) => css`
    border: 2px solid ${({ theme }) => theme.colors.primary};
    background: ${({ theme }) => isSelected && theme.colors.primary};
  `,
};

const TagBadge = styled(Badge)<TagStyleProps>`
  ${({ variant, isSelected }) => tagColorStyles[variant ?? 'ETC'](isSelected)};
  white-space: nowrap;
`;
