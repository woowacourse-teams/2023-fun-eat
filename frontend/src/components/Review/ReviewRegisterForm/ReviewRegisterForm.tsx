import { Button, Checkbox, Divider, Heading, Spacing, theme } from '@fun-eat/design-system';
import { FormEvent, useState } from 'react';
import styled from 'styled-components';

import ReviewImageUploader from '../ReviewImageUploader/ReviewImageUploader';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import { MIN_DISPLAYED_TAGS_LENGTH } from '@/constants';
import { useReviewTextarea, useSelectedTags } from '@/hooks/review';
import useStarRating from '@/hooks/useStarRate';
import type { ProductDetail } from '@/types/product';

interface ReviewRegisterFormProps {
  product: ProductDetail;
  close: () => void;
}

const ReviewRegisterForm = ({ product, close }: ReviewRegisterFormProps) => {
  const { rating, handleRating } = useStarRating();
  const { selectedTags, toggleTagSelection } = useSelectedTags(MIN_DISPLAYED_TAGS_LENGTH);
  const { content, handleReviewInput } = useReviewTextarea();
  const [rebuy, setRebuy] = useState(false);

  const handleRebuy = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRebuy(event.target.checked);
  };

  const handleSubmit =
    //: React.FormEventHandler<HTMLFormElement>
    (event: any) => {
      event.preventDefault();
      console.log(rating);
      console.log(selectedTags);
      console.log(content);
      console.log(rebuy);
    };

  return (
    <ReviewRegisterFormContainer>
      <ReviewHeading>리뷰 작성</ReviewHeading>
      <CloseButton variant="transparent" onClick={close}>
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={product.name} image={product.image} />
      </ProductOverviewItemWrapper>
      <Divider variant="disabled" css="height:4px;" />
      <RegisterForm>
        <ReviewImageUploader />
        <Spacing size={60} />
        <StarRate rating={rating} handleRating={handleRating} />
        <Spacing size={60} />
        <ReviewTagList selectedTags={selectedTags} toggleTagSelection={toggleTagSelection} />
        <Spacing size={60} />
        <ReviewTextarea content={content} onReviewInput={handleReviewInput} />
        <Spacing size={80} />
        <Checkbox weight="bold" onChange={handleRebuy}>
          재구매할 생각이 있으신가요?
        </Checkbox>
        <Spacing size={16} />
        <Button customWidth="100%" customHeight="60px" size="xl" weight="bold" onClick={handleSubmit}>
          등록하기
        </Button>
      </RegisterForm>
    </ReviewRegisterFormContainer>
  );
};

export default ReviewRegisterForm;

const ReviewRegisterFormContainer = styled.div`
  position: relative;
  height: 100%;
`;

const ReviewHeading = styled(Heading)`
  height: 80px;
  text-align: center;
  font-size: 2.4rem;
  line-height: 80px;
`;

const CloseButton = styled(Button)`
  position: absolute;
  top: 24px;
  right: 32px;
`;

const ProductOverviewItemWrapper = styled.div`
  margin: 15px 0;
`;

const RegisterForm = styled.form`
  padding: 50px 20px;
`;
