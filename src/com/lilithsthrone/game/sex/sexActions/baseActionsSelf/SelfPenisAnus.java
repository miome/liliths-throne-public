package com.lilithsthrone.game.sex.sexActions.baseActionsSelf;

import com.lilithsthrone.game.character.attributes.CorruptionLevel;
import com.lilithsthrone.game.character.body.valueEnums.PenetrationModifier;
import com.lilithsthrone.game.character.body.valueEnums.PenisLength;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.ArousalIncrease;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.game.sex.SexAreaPenetration;
import com.lilithsthrone.game.sex.SexPace;
import com.lilithsthrone.game.sex.SexParticipantType;
import com.lilithsthrone.game.sex.sexActions.SexAction;
import com.lilithsthrone.game.sex.sexActions.SexActionType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;

/**
 * @since 0.4.9
 * @version 0.4.10
 * @author X&Y
 */
public class SelfPenisAnus {

	public static final SexAction SELF_AUTOPENETRATION_ANUS_START = new SexAction(
			SexActionType.START_ONGOING,
			ArousalIncrease.FOUR_HIGH,
			ArousalIncrease.FOUR_HIGH,
			CorruptionLevel.TWO_HORNY,
			Util.newHashMapOfValues(new Value<>(SexAreaOrifice.ANUS, SexAreaPenetration.PENIS)),
			SexParticipantType.SELF) {
		@Override
		public boolean isBaseRequirementsMet() {
			return !Main.sex.getCharacterPerformingAction().isTaur()
					&& Main.sex.getCharacterPerformingAction().hasPenisModifier(PenetrationModifier.PREHENSILE)
					&& Main.sex.getCharacterPerformingAction().getPenisRawSizeValue()>=PenisLength.FOUR_HUGE.getMinimumValue();
		}
        @Override
		public String getActionTitle() {
			return "Self-peg";
		}

		@Override
		public String getActionDescription() {
			return "Start pegging [npc.her] [npc.ass+] with [npc.her] [npc.cock+].";
		}

		@Override
		public String getDescription() {
			return (UtilText.returnStringAtRandom(
					"Snaking [npc.her] [npc.cock] around between [npc.her] [npc.legs], [npc.name] [npc.verb(tease)] the tip over the entrance to [npc.her] [npc.asshole+], [npc.moaning] in delight before thrusting it deep inside [npc.herself].",
					"[npc.Name] [npc.verb(snake)] [npc.her] [npc.cock] up between [npc.her] legs, [npc.moaning] in delight as [npc.she] [npc.verb(force)] it deep into [npc.her] inviting [npc.asshole].",
					"Sliding the tip of [npc.her] [npc.cock+] between the folds of [npc.her] neglected [npc.ass], [npc.name] suddenly [npc.verb(thrust)] it deep inside, letting out [npc.a_moan+] as [npc.she] [npc.verb(start)] fucking [npc.her] own [npc.ass].",
					"[npc.Name] eagerly [npc.verb(thrust)] [npc.her] [npc.cock+] deep into [npc.her] needy [npc.ass], letting out a series of [npc.moans+] as [npc.she] [npc.verb(start)] fucking [npc.her] own [npc.ass]."));
		}
	};
	
	public static final SexAction DOM_SELF_AUTOPENETRATION_ANUS_GENTLE = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.FOUR_HIGH,
			ArousalIncrease.FOUR_HIGH,
			CorruptionLevel.ONE_VANILLA,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.DOM_GENTLE) {

		@Override
		public String getActionTitle() {
			return "Gently peg yourself";
		}

		@Override
		public String getActionDescription() {
			return "Gently peg [npc.her] [npc.ass+] with [npc.her] [npc.cock+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
					"[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] slowly [npc.verb(push)] [npc.her] [npc.cock] deep inside [npc.her] [npc.ass+].",
					"Gently pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+], [npc.name] [npc.verb(start)] letting out a series of delighted [npc.moans] as [npc.she] rhythmically [npc.verb(fuck)] [npc.her] own [npc.ass].",
					"Slowly driving [npc.her] [npc.cock] deep inside [npc.her] [npc.ass+], [npc.name] [npc.verb(let)] out a little whimper as [npc.she] [npc.verb(start)] gently fucking [npc.her] own [npc.ass].",
					"Focusing on pleasuring [npc.herself], [npc.name] [npc.verb(start)] gently pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+].");
		}
		
	};
	
	public static final SexAction DOM_SELF_AUTOPENETRATION_ANUS_NORMAL = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.FOUR_HIGH,
			ArousalIncrease.FOUR_HIGH,
			CorruptionLevel.ONE_VANILLA,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.DOM_NORMAL) {
		
		@Override
		public String getActionTitle() {
			return "Self-peg";
		}

		@Override
		public String getActionDescription() {
			return "Concentrate on fucking [npc.her] own [npc.ass] with [npc.her] [npc.cock+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
					"[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] greedily [npc.verb(push)] [npc.her] [npc.cock] deep inside [npc.her] [npc.ass+].",
					"Pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+], [npc.name] [npc.verb(start)] letting out a series of delighted [npc.moans] as [npc.she] rhythmically [npc.verb(fuck)] [npc.her] own [npc.ass].",
					"Driving [npc.her] [npc.cock] deep inside [npc.her] [npc.ass], [npc.name] [npc.verb(let)] out [npc.a_moan] as [npc.she] [npc.verb(start)] happily pegging [npc.her] own [npc.ass].",
					"Focusing on the pleasure [npc.sheIs] giving [npc.herself] between [npc.her] [npc.legs], [npc.name] [npc.verb(start)] pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+].");
		}
	};
	
	public static final SexAction DOM_SELF_AUTOPENETRATION_ANUS_ROUGH = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.FOUR_HIGH,
			ArousalIncrease.FOUR_HIGH,
			CorruptionLevel.ONE_VANILLA,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.DOM_ROUGH) {
		
		@Override
		public String getActionTitle() {
			return "Roughly peg yourself";
		}

		@Override
		public String getActionDescription() {
			return "Roughly peg [npc.herself] with [npc.her] [npc.cock+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
					"[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] roughly [npc.verb(slam)] [npc.her] [npc.cock] deep inside [npc.her] [npc.ass+], before starting to roughly peg [npc.herself].",
					"Roughly pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+], [npc.name] [npc.verb(start)] letting out a series of delighted [npc.moans] as [npc.she] ruthlessly [npc.verb(fuck)] [npc.her] own [npc.ass].",
					"Driving [npc.her] [npc.cock] deep inside [npc.her] [npc.ass], [npc.name] [npc.verb(let)] out [npc.a_moan] as [npc.she] [npc.verb(start)] roughly grinding it up inside [npc.herself].",
					"Focusing on the pleasure [npc.sheIs] giving [npc.herself] between [npc.her] [npc.legs], [npc.name] [npc.verb(start)] roughly slamming [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+].");
		}
	};
	
	public static final SexAction SUB_SELF_AUTOPENETRATION_ANUS_NORMAL = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.FOUR_HIGH,
			ArousalIncrease.FOUR_HIGH,
			CorruptionLevel.ONE_VANILLA,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.SUB_NORMAL) {
		
		@Override
		public String getActionTitle() {
			return "Self-peg";
		}

		@Override
		public String getActionDescription() {
			return "Concentrate on fucking [npc.her] own [npc.ass] with [npc.her] [npc.cock+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
					"[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] greedily [npc.verb(push)] [npc.her] [npc.cock] deep inside [npc.her] [npc.ass+].",
					"Pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+], [npc.name] [npc.verb(start)] letting out a series of delighted [npc.moans] as [npc.she] rhythmically [npc.verb(fuck)] [npc.her] own [npc.ass].",
					"Driving [npc.her] [npc.cock] deep inside [npc.her] [npc.ass], [npc.name] [npc.verb(let)] out [npc.a_moan] as [npc.she] [npc.verb(start)] happily fucking [npc.her] own [npc.ass].",
					"Focusing on the pleasure [npc.sheIs] giving [npc.herself] between [npc.her] [npc.legs], [npc.name] [npc.verb(start)] pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+].");
		}
		
	};
	
	public static final SexAction SUB_SELF_AUTOPENETRATION_ANUS_EAGER = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.FOUR_HIGH,
			ArousalIncrease.FOUR_HIGH,
			CorruptionLevel.ONE_VANILLA,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.SUB_EAGER) {
		
		@Override
		public String getActionTitle() {
			return "Eagerly peg yourself";
		}

		@Override
		public String getActionDescription() {
			return "Eagerly peg [npc.herself] with [npc.her] [npc.cock+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
					"[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] eagerly [npc.verb(slam)] [npc.her] [npc.cock] deep inside [npc.her] [npc.ass+], before starting to desperately peg [npc.herself].",
					"Enthusiastically pumping [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+], [npc.name] [npc.verb(start)] letting out a series of delighted [npc.moans] as [npc.she] frantically [npc.verb(fuck)] [npc.her] own [npc.ass].",
					"Desperately driving [npc.her] [npc.cock] deep inside [npc.her] [npc.ass], [npc.name] [npc.verb(let)] out [npc.a_moan] as [npc.she] [npc.verb(start)] eagerly grinding it up inside [npc.herself].",
					"Focusing on the pleasure [npc.sheIs] giving [npc.herself] between [npc.her] [npc.legs], [npc.name] eagerly [npc.verb(start)] slamming [npc.her] [npc.cock] in and out of [npc.her] [npc.ass+].");
		}
	};
	
	public static final SexAction SELF_AUTOPENETRATION_ANUS_STOP_PENETRATION = new SexAction(
			SexActionType.STOP_ONGOING,
			ArousalIncrease.TWO_LOW,
			ArousalIncrease.TWO_LOW,
			CorruptionLevel.ONE_VANILLA,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF) {
		
		@Override
		public String getActionTitle() {
			return "Stop pegging yourself";
		}

		@Override
		public String getActionDescription() {
			return "Stop fucking [npc.her] own [npc.ass] with [npc.her] [npc.cock].";
		}

		@Override
		public String getDescription() {
			return "Letting out [npc.a_moan], [npc.name] [npc.verb(slide)] [npc.her] [npc.cock+] out of [npc.her] [npc.ass+].";
		}
	};
}
