import { theme } from '@fun-eat/design-system';
import type { ComponentPropsWithoutRef, CSSProperties } from 'react';

export const SVG_ICON_VARIANTS = [
  'recipe',
  'list',
  'profile',
  'search',
  'arrow',
  'bookmark',
  'bookmarkFilled',
  'review',
  'star',
  'favorite',
  'favoriteFilled',
  'home',
  'sort',
  'close',
] as const;
export type SvgIconVariant = (typeof SVG_ICON_VARIANTS)[number];

interface SvgIconProps extends ComponentPropsWithoutRef<'svg'> {
  /**
   * SvgSprite 컴포넌트의 symbol id입니다.
   */
  variant: SvgIconVariant;
  /**
   * SvgIcon의 색상입니다. (기본값 gray4)
   */
  color?: CSSProperties['color'];
  /**
   * SvgIcon의 너비입니다. (기본값 24)
   */
  width?: number;
  /**
   * SvgIcon의 높이입니다. (기본값 24)
   */
  height?: number;
}

const SvgIcon = ({ variant, width = 24, height = 24, color = theme.colors.gray4, ...props }: SvgIconProps) => {
  return (
    <svg width={width} height={height} fill={color} {...props}>
      <use href={`#${variant}`} />
    </svg>
  );
};

export default SvgIcon;
