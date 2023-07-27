import { Button, Checkbox, Divider, Heading, Spacing, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import ReviewImageUploader from '../ReviewImageUploader/ReviewImageUploader';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import type { ProductDetail } from '@/types/product';

interface ReviewRegisterFormProps {
  product: ProductDetail;
  close: () => void;
}

const ReviewRegisterForm = ({ product, close }: ReviewRegisterFormProps) => {
  return (
    <ReviewRegisterFormContainer>
      <RegisterFormHeader>
        <Heading css="font-size:2.4rem">리뷰 작성</Heading>
        <CloseButton variant="transparent" onClick={close}>
          <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
        </CloseButton>
      </RegisterFormHeader>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={product.name} image={product.image} />
      </ProductOverviewItemWrapper>
      <Divider variant="disabled" css="height:4px;" />
      <RegisterForm>
        <ReviewImageUploader />
        <Spacing size={60} />
        <StarRate />
        <Spacing size={60} />
        <ReviewTagList />
        <Spacing size={60} />
        <ReviewTextarea />
        <Spacing size={80} />
        <Checkbox weight="bold">재구매할 생각이 있으신가요?</Checkbox>
        <Spacing size={16} />
        <Button customWidth="100%" customHeight="60px" size="xl" weight="bold">
          등록하기
        </Button>
      </RegisterForm>
    </ReviewRegisterFormContainer>
  );
};

export default ReviewRegisterForm;

const ReviewRegisterFormContainer = styled.div`
  height: 100%;
  padding: 30px;
`;

const RegisterFormHeader = styled.header`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  position: relative;
  height: 80px;
`;

const CloseButton = styled(Button)`
  position: absolute;
  right: 30px;
`;

const ProductOverviewItemWrapper = styled.div`
  margin: 15px 0;
`;

const RegisterForm = styled.form`
  padding: 50px 0;
`;
