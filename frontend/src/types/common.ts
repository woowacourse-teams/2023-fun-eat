import type { SvgIconVariant } from '@/components/Common/Svg/SvgIcon';
import type { PATH } from '@/constants/path';

export interface Category {
  id: number;
  name: string;
}

export interface Tag {
  id: number;
  name: string;
}

export interface NavigationMenu {
  variant: SvgIconVariant;
  name: '검색' | '목록' | '홈' | '꿀조합' | '마이';
  path: (typeof PATH)[keyof typeof PATH];
}
