import {PolymerElement,html} from '@polymer/polymer/polymer-element.js';
import cytoscape from 'cytoscape/dist/cytoscape.esm.min.js';

let cyMap = new Map();

class CytoscapeGraph extends PolymerElement {
    static get template() {
        return html`
            <style>
                #cy {
                  width: 300px;
                  height: 300px;
                  display: block;
                }
            </style>
            <div id$="[[taskId]]" style="display: none;"></div>
            <div id="cy">
            </div>`
    }

    static get is() {return 'cytoscape-graph'}

    attached() {

        let cy = cytoscape({

          container: this.shadowRoot.querySelector("#cy"),

          elements: [

          ],

          style: [
            {
              selector: 'node',
              style: {
                'background-color': '#1676F3',
                'label': 'data(id)',
                'color': '#000',
                'font-size': '18px'
              }
            },


            {
              selector: 'edge',
              style: {
                'width': 3,
                'curve-style': 'bezier',
                'line-color': '#ссс',
                'target-arrow-color': '#666',
                'target-arrow-shape': 'triangle'
              }
            }
          ],

        });

        cy.userPanningEnabled(false);

        return cy;
    }

    ready() {
        super.ready();
        cyMap.set(this.taskId, this.attached());
    }

    setCurrentElement(value) {
        let cy = cyMap.get(this.taskId);
        cy.nodes('nodes').style('background-color', '#1676F3');
        cy.nodes('nodes').style('font-weight', 'normal');
        cy.nodes(value).style('background-color', '#F05026');
        cy.nodes(value).style('font-weight', 'bold');
    }

    setData(nodes, edges) {
        var nodesArray = JSON.parse(nodes);
        var edgesArray = JSON.parse(edges);
        let cy = cyMap.get(this.taskId);
        for (var i = 0; i < nodesArray.nodes.length; i++) {
            cy.add({ group: 'nodes', data: { id: nodesArray.nodes[i].id }});
        }
        for (var i = 0; i < edgesArray.edges.length; i++) {
            cy.add({ group: 'edges', data: { source: edgesArray.edges[i].source, target: edgesArray.edges[i].target }});
        }

        var layout = cy.layout({
            name: 'grid',
            rows: 2
        });

        layout.run();
    }
}

customElements.define(CytoscapeGraph.is, CytoscapeGraph);