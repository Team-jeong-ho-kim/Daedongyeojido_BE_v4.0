package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class OnePagerNotFoundException extends DaedongException {
  public static final DaedongException EXCEPTION = new OnePagerNotFoundException();

  private OnePagerNotFoundException() {
    super(ErrorCode.ONE_PAGER_NOT_FOUND);
  }
}
