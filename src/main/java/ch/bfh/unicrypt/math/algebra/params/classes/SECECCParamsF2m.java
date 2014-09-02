/*
 * UniCrypt
 *
 *  UniCrypt(tm) : Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
 *  Copyright (C) 2014 Bern University of Applied Sciences (BFH), Research Institute for
 *  Security in the Information Society (RISIS), E-Voting Group (EVG)
 *  Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *  Licensed under Dual License consisting of:
 *  1. GNU Affero General Public License (AGPL) v3
 *  and
 *  2. Commercial license
 *
 *
 *  1. This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *  2. Licensees holding valid commercial licenses for UniCrypt may use this file in
 *   accordance with the commercial license agreement provided with the
 *   Software or, alternatively, in accordance with the terms contained in
 *   a written agreement between you and Bern University of Applied Sciences (BFH), Research Institute for
 *   Security in the Information Society (RISIS), E-Voting Group (EVG)
 *   Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *   For further information contact <e-mail: unicrypt@bfh.ch>
 *
 *
 * Redistributions of files must retain the above copyright notice.
 */
package ch.bfh.unicrypt.math.algebra.params.classes;

import ch.bfh.unicrypt.math.algebra.additive.classes.StandardECPolynomialField;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.PolynomialElement;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.PolynomialField;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.PolynomialRing;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModPrime;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModTwo;
import ch.bfh.unicrypt.math.algebra.params.interfaces.StandardECPolynomialFieldParams;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * 
 * @author Christian Lutz
 * 
 */
public enum SECECCParamsF2m implements StandardECPolynomialFieldParams {
	//TODO: remove sectTest 
	sectTest("13", "3", "1", "8", "d", "10", "2"), 
	sect113r1(
			"20000000000000000000000000201", "003088250CA6E7C7FE649CE85820F7",
			"00E8BEE4D3E2260744188BE0E9C723", "009D73616F35F4AB1407D73562C10F",
			"00A52830277958EE84D1315ED31886", "0100000000000000D9CCEC8A39E56F",
			"2"), sect113r2("20000000000000000000000000201",
			"00689918DBEC7E5A0DD6DFC0AA55C7", "0095E9A9EC9B297BD4BF36E059184F",
			"01A57A6A7B26CA5EF52FCDB8164797", "00B3ADC94ED1FE674C06E695BABA1D",
			"010000000000000108789B2496AF93", "2"), sect131r1(
			"80000000000000000000000000000010d",
			"07A11B09A76B562144418FF3FF8C2570B8",
			"0217C05610884B63B9C6C7291678F9D341",
			"0081BAF91FDF9833C40F9C181343638399",
			"078C6E7EA38C001F73C8134B1B4EF9E150",
			"0400000000000000023123953A9464B54D", "2"), sect131r2(
			"80000000000000000000000000000010d",
			"03E5A88919D7CAFCBF415F07C2176573B2",
			"04B8266A46C55657AC734CE38F018F2192",
			"0356DCD8F2F95031AD652D23951BB366A8",
			"0648F06D867940A5366D9E265DE9EB240F",
			"0400000000000000016954A233049BA98F", "2"), sect163k1(
			"800000000000000000000000000000000000000c9", "1", "1",
			"02FE13C0537BBC11ACAA07D793DE4E6D5E5C94EEE8",
			"0289070FB05D38FF58321F2E800536D538CCDAA3D9",
			"04000000000000000000020108A2E0CC0D99F8A5EF", "2"), sect163r1(
			"800000000000000000000000000000000000000c9",
			"07B6882CAAEFA84F9554FF8428BD88E246D2782AE2",
			"0713612DCDDCB40AAB946BDA29CA91F73AF958AFD9",
			"0369979697AB43897789566789567F787A7876A654",
			"00435EDB42EFAFB2989D51FEFCE3C80988F41FF883",
			"03FFFFFFFFFFFFFFFFFFFF48AAB689C29CA710279B", "2"), sect163r2(
			"800000000000000000000000000000000000000c9",
			"000000000000000000000000000000000000000001",
			"020A601907B8C953CA1481EB10512F78744A3205FD",
			"03F0EBA16286A2D57EA0991168D4994637E8343E36",
			"00D51FBC6C71A0094FA2CDD545B11C5C0C797324F1",
			"040000000000000000000292FE77E70C12A4234C33", "2"), sect193r1(
			"2000000000000000000000000000000000000000000008001",
			"0017858FEB7A98975169E171F77B4087DE098AC8A911DF7B01",
			"00FDFB49BFE6C3A89FACADAA7A1E5BBC7CC1C2E5D831478814",
			"01F481BC5F0FF84A74AD6CDF6FDEF4BF6179625372D8C0C5E1",
			"0025E399F2903712CCF3EA9E3A1AD17FB0B3201B6AF7CE1B05",
			"01000000000000000000000000C7F34A778F443ACC920EBA49", "2"), sect193r2(
			"2000000000000000000000000000000000000000000008001",
			"0163F35A5137C2CE3EA6ED8667190B0BC43ECD69977702709B",
			"00C9BB9E8927D4D64C377E2AB2856A5B16E3EFB7F61D4316AE",
			"00D9B67D192E0367C803F39E1A7E82CA14A651350AAE617E8F",
			"01CE94335607C304AC29E7DEFBD9CA01F596F927224CDECF6C",
			"010000000000000000000000015AAB561B005413CCD4EE99D5", "2"), sect233k1(
			"20000000000000000000000000000000000000004000000000000000001", "0",
			"1",
			"017232BA853A7E731AF129F22FF4149563A419C26BF50A4C9D6EEFAD6126",
			"01DB537DECE819B7F70F555A67C427A8CD9BF18AEB9B56E0C11056FAE6A3",
			"8000000000000000000000000000069D5BB915BCD46EFB1AD5F173ABDF", "4"), sect233r1(
			"20000000000000000000000000000000000000004000000000000000001", "1",
			"0066647EDE6C332C7F8C0923BB58213B333B20E9CE4281FE115F7D8F90AD",
			"00FAC9DFCBAC8313BB2139F1BB755FEF65BC391F8B36F8F8EB7371FD558B",
			"01006A08A41903350678E58528BEBF8A0BEFF867A7CA36716F7E01F81052",
			"01000000000000000000000000000013E974E72F8A6922031D2603CFE0D7", "2"), sect239k1(
			"800000000000000000004000000000000000000000000000000000000001",
			"0", "1",
			"29A0B6A887A983E9730988A68727A8B2D126C44CC2CC7B2A6555193035DC",
			"76310804F12E549BDB011C103089E73510ACB275FC312A5DC6B76553F0CA",
			"2000000000000000000000000000005A79FEC67CB6E91F1C1DA800E478A5", "4"), sect283k1(
			"800000000000000000000000000000000000000000000000000000000000000000010a1",
			"0",
			"1",
			"0503213F78CA44883F1A3B8162F188E553CD265F23C1567A16876913B0C2AC2458492836",
			"01CCDA380F1C9E318D90F95D07E5426FE87E45C0E8184698E45962364E34116177DD2259",
			"01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE9AE2ED07577265DFF7F", "4"), sect283r1(
			"800000000000000000000000000000000000000000000000000000000000000000010a1",
			"1",
			"027B680AC8B8596DA5A4AF8A19A0303FCA97FD7645309FA2A581485AF6263E313B79A2F5",
			"05F939258DB7DD90E1934F8C70B0DFEC2EED25B8557EAC9C80E2E198F8CDBECD86B12053",
			"03676854FE24141CB98FE6D4B20D02B4516FF702350EDDB0826779C813F0DF45BE8112F4",
			"03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEF90399660FC938A90165B042A7CEFADB30",
			"2"), sect409k1(
			"2000000000000000000000000000000000000000000000000000000000000000000000000000000008000000000000000000001",
			"0",
			"1",
			"0060F05F658F49C1AD3AB1890F7184210EFD0987E307C84C27ACCFB8F9F67CC2C460189EB5AAAA62EE222EB1B35540CFE9023746",
			"01E369050B7C4E42ACBA1DACBF04299C3460782F918EA427E6325165E9EA10E3DA5F6C42E9C55215AA9CA27A5863EC48D8E0286B",
			"7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE5F83B2D4EA20400EC4557D5ED3E3E7CA5B4B5C83B8E01E5FCF",
			"4"), sect409r1(
			"2000000000000000000000000000000000000000000000000000000000000000000000000000000008000000000000000000001",
			"1",
			"0021A5C2C8EE9FEB5C4B9A753B7B476B7FD6422EF1F3DD674761FA99D6AC27C8A9A197B272822F6CD57A55AA4F50AE317B13545F",
			"015D4860D088DDB3496B0C6064756260441CDE4AF1771D4DB01FFE5B34E59703DC255A868A1180515603AEAB60794E54BB7996A7",
			"0061B1CFAB6BE5F32BBFA78324ED106A7636B9C5A7BD198D0158AA4F5488D08F38514F1FDF4B4F40D2181B3681C364BA0273C706",
			"010000000000000000000000000000000000000000000000000001E2AAD6A612F33307BE5FA47C3C9E052F838164CD37D9A21173",
			"2"), sect571k1(
			"80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000425",
			"0",
			"1",
			"026EB7A859923FBC82189631F8103FE4AC9CA2970012D5D46024804801841CA44370958493B205E647DA304DB4CEB08CBBD1BA39494776FB988B47174DCA88C7E2945283A01C8972",
			"0349DC807F4FBF374F4AEADE3BCA95314DD58CEC9F307A54FFC61EFC006D8A2C9D4979C0AC44AEA74FBEBBB9F772AEDCB620B01A7BA7AF1B320430C8591984F601CD4C143EF1C7A3",
			"020000000000000000000000000000000000000000000000000000000000000000000000131850E1F19A63E4B391A8DB917F4138B630D84BE5D639381E91DEB45CFE778F637C1001",
			"4"), sect571r1(
			"80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000425",
			"1",
			"02F40E7E2221F295DE297117B7F3D62F5C6A97FFCB8CEFF1CD6BA8CE4A9A18AD84FFABBD8EFA59332BE7AD6756A66E294AFD185A78FF12AA520E4DE739BACA0C7FFEFF7F2955727A",
			"0303001D34B856296C16C0D40D3CD7750A93D1D2955FA80AA5F40FC8DB7B2ABDBDE53950F4C0D293CDD711A35B67FB1499AE60038614F1394ABFA3B4C850D927E1E7769C8EEC2D19",
			"037BF27342DA639B6DCCFFFEB73D69D78C6C27A6009CBBCA1980F8533921E8A684423E43BAB08A576291AF8F461BB2A8B3531D2F0485C19B16E2F1516E23DD3C1A4827AF1B8AC15B",
			"03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE661CE18FF55987308059B186823851EC7DD9CA1161DE93D5174D66E8382E9BB2FE84E47",
			"2");

	public String polynom, a, b, gx, gy, order, h;

	private SECECCParamsF2m(String polynom, String a, String b, String gx,
			String gy, String order, String h) {
		this.polynom = polynom;
		this.a = a;
		this.b = b;
		this.gx = gx;
		this.gy = gy;
		this.order = order;
		this.h = h;
	}

	private PolynomialElement<ZModTwo> getPolynomeFromString(String p) {
		BigInteger bitString = new BigInteger(p, 16);

		// Read bits and create a BigInteger ArrayList
		ArrayList<BigInteger> arrayBigInteger = new ArrayList<BigInteger>();
		for (Character s : bitString.toString(2).toCharArray()) {
			arrayBigInteger.add(0,new BigInteger(s.toString()));

		}

		// Convert ArrayList BigInteger array and get element
		BigInteger[] coeffs = {};
		coeffs = arrayBigInteger.toArray(coeffs);

		PolynomialElement<ZModTwo> irreduciblePolynom = PolynomialRing
				.getInstance(ZModTwo.getInstance()).getElement(coeffs);
		return irreduciblePolynom;

	}
	
	public PolynomialElement getIrreduciblePolynome(){
		return getPolynomeFromString(polynom);
	}

	@Override
	public PolynomialField getFiniteField() {
		PolynomialElement irreduciblePolynomialElement = this
				.getPolynomeFromString(this.polynom);
		return PolynomialField.getInstance(ZModTwo.getInstance(),
				irreduciblePolynomialElement);
	}

	@Override
	public BigInteger getP() {
		return null;
	}

	@Override
	public PolynomialElement getA() {
		return (PolynomialElement) getFiniteField().getElement(getPolynomeFromString(a).getValue());
	}

	@Override
	public PolynomialElement getB() {
		return (PolynomialElement) getFiniteField().getElement(getPolynomeFromString(b).getValue());
	}

	@Override
	public PolynomialElement getGx() {
		return (PolynomialElement) getFiniteField().getElement(getPolynomeFromString(gx).getValue());
	}

	@Override
	public PolynomialElement getGy() {
		return (PolynomialElement) getFiniteField().getElement(getPolynomeFromString(gy).getValue());
	}

	@Override
	public BigInteger getOrder() {
		return new BigInteger(order, 16);
	}

	@Override
	public BigInteger getH() {
		return new BigInteger(h, 16);
	}

	public static SECECCParamsF2m getFromString(String s) {
		for (SECECCParamsF2m parameter : SECECCParamsF2m.values()) {
			if (parameter.name().equals(s)) {
				return parameter;
			}
		}

		throw new IllegalArgumentException("No Enum with name: " + s);
	}

}
