#!/usr/bin/env python3
from __future__ import annotations

import shutil
import zipfile
from pathlib import Path
from xml.etree import ElementTree as ET


ROOT = Path(__file__).resolve().parents[1]
PPTX = ROOT / "outputs/pptx_final/天空之山·坎卦智慧广场_FinalPPT_20260505.pptx"
ASSETS = ROOT / "outputs/pptx_final/assets"
WORK = ROOT / "outputs/pptx_final/.pptx_work"

NS = {
    "p": "http://schemas.openxmlformats.org/presentationml/2006/main",
    "a": "http://schemas.openxmlformats.org/drawingml/2006/main",
    "r": "http://schemas.openxmlformats.org/officeDocument/2006/relationships",
    "rel": "http://schemas.openxmlformats.org/package/2006/relationships",
    "ct": "http://schemas.openxmlformats.org/package/2006/content-types",
}

for prefix, uri in NS.items():
    if prefix not in {"rel", "ct"}:
        ET.register_namespace(prefix, uri)


SLIDE_W = 12_192_000
SLIDE_H = 6_858_000


def q(ns: str, tag: str) -> str:
    return f"{{{NS[ns]}}}{tag}"


def pic_xml(name: str, rid: str, x: int, y: int, cx: int, cy: int, shape_id: int) -> ET.Element:
    pic = ET.Element(q("p", "pic"))
    nv = ET.SubElement(pic, q("p", "nvPicPr"))
    ET.SubElement(nv, q("p", "cNvPr"), {"id": str(shape_id), "name": name})
    ET.SubElement(nv, q("p", "cNvPicPr"))
    ET.SubElement(nv, q("p", "nvPr"))
    blip_fill = ET.SubElement(pic, q("p", "blipFill"))
    ET.SubElement(blip_fill, q("a", "blip"), {q("r", "embed"): rid})
    stretch = ET.SubElement(blip_fill, q("a", "stretch"))
    ET.SubElement(stretch, q("a", "fillRect"))
    sp_pr = ET.SubElement(pic, q("p", "spPr"))
    xfrm = ET.SubElement(sp_pr, q("a", "xfrm"))
    ET.SubElement(xfrm, q("a", "off"), {"x": str(x), "y": str(y)})
    ET.SubElement(xfrm, q("a", "ext"), {"cx": str(cx), "cy": str(cy)})
    ET.SubElement(sp_pr, q("a", "prstGeom"), {"prst": "rect"}).append(ET.Element(q("a", "avLst")))
    return pic


def ensure_png_content_type() -> None:
    path = WORK / "[Content_Types].xml"
    tree = ET.parse(path)
    root = tree.getroot()
    has_png = any(el.attrib.get("Extension") == "png" for el in root.findall(q("ct", "Default")))
    if not has_png:
        ET.SubElement(root, q("ct", "Default"), {"Extension": "png", "ContentType": "image/png"})
    tree.write(path, encoding="UTF-8", xml_declaration=True)


def next_rid(rels_root: ET.Element) -> str:
    max_id = 0
    for rel in rels_root.findall(q("rel", "Relationship")):
        rid = rel.attrib.get("Id", "")
        if rid.startswith("rId") and rid[3:].isdigit():
            max_id = max(max_id, int(rid[3:]))
    return f"rId{max_id + 1}"


def add_image_to_slide(slide_no: int, image_name: str, x: int, y: int, cx: int, cy: int, behind_text: bool = True) -> None:
    media_dir = WORK / "ppt/media"
    media_dir.mkdir(parents=True, exist_ok=True)
    shutil.copy2(ASSETS / image_name, media_dir / image_name)

    rels_path = WORK / f"ppt/slides/_rels/slide{slide_no}.xml.rels"
    rel_tree = ET.parse(rels_path)
    rels_root = rel_tree.getroot()
    rid = next_rid(rels_root)
    ET.SubElement(
        rels_root,
        q("rel", "Relationship"),
        {
            "Id": rid,
            "Type": "http://schemas.openxmlformats.org/officeDocument/2006/relationships/image",
            "Target": f"../media/{image_name}",
        },
    )
    rel_tree.write(rels_path, encoding="UTF-8", xml_declaration=True)

    slide_path = WORK / f"ppt/slides/slide{slide_no}.xml"
    slide_tree = ET.parse(slide_path)
    slide_root = slide_tree.getroot()
    sp_tree = slide_root.find(".//p:spTree", NS)
    if sp_tree is None:
        raise RuntimeError(f"slide{slide_no}: spTree not found")

    shape_id = 900 + slide_no
    pic = pic_xml(image_name, rid, x, y, cx, cy, shape_id)
    insert_at = 2 if behind_text else len(sp_tree)
    sp_tree.insert(insert_at, pic)
    slide_tree.write(slide_path, encoding="UTF-8", xml_declaration=True)


def rebuild() -> None:
    if WORK.exists():
        shutil.rmtree(WORK)
    WORK.mkdir(parents=True)
    with zipfile.ZipFile(PPTX) as zf:
        zf.extractall(WORK)

    ensure_png_content_type()

    # Full-bleed cover image behind title text.
    add_image_to_slide(1, "01_aerial_masterplan.png", 0, 0, SLIDE_W, SLIDE_H, True)

    # Right-side visual panels on content slides.
    x = 6_500_000
    y = 1_250_000
    cx = 5_250_000
    cy = 3_650_000
    add_image_to_slide(4, "01_aerial_masterplan.png", x, y, cx, cy, True)
    add_image_to_slide(5, "02_kan_gate_entrance.png", x, y, cx, cy, True)
    add_image_to_slide(6, "03_water_mirror_courtyard.png", x, y, cx, cy, True)
    add_image_to_slide(10, "04_night_waterlight_street.png", x, y, cx, cy, True)

    tmp = PPTX.with_suffix(".tmp.pptx")
    if tmp.exists():
        tmp.unlink()
    with zipfile.ZipFile(tmp, "w", compression=zipfile.ZIP_DEFLATED) as zf:
        for path in WORK.rglob("*"):
            if path.is_file():
                zf.write(path, path.relative_to(WORK).as_posix())
    tmp.replace(PPTX)
    shutil.rmtree(WORK)


if __name__ == "__main__":
    rebuild()
